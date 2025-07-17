package com.lavy.redbook.note.biz.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Preconditions;
import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.JsonUtils;
import com.lavy.redbook.note.api.vo.req.FindNoteDetailReqVO;
import com.lavy.redbook.note.api.vo.req.PublishNoteReqVO;
import com.lavy.redbook.note.api.vo.resp.FindNoteDetailRspVO;
import com.lavy.redbook.note.biz.constant.RedisKeyConstants;
import com.lavy.redbook.note.biz.domain.dataobject.NoteDO;
import com.lavy.redbook.note.biz.domain.dataobject.NoteDO.NoteDOBuilder;
import com.lavy.redbook.note.biz.domain.dataobject.TopicDO;
import com.lavy.redbook.note.biz.domain.mapper.NoteDOMapper;
import com.lavy.redbook.note.biz.enums.NoteStatusEnum;
import com.lavy.redbook.note.biz.enums.NoteTypeEnum;
import com.lavy.redbook.note.biz.enums.NoteVisibleEnum;
import com.lavy.redbook.note.biz.enums.ResponseCodeEnum;
import com.lavy.redbook.note.biz.rpc.DistributedIdGeneratorRpcService;
import com.lavy.redbook.note.biz.rpc.KvRpcService;
import com.lavy.redbook.note.biz.rpc.UserRpcService;
import com.lavy.redbook.note.biz.service.NoteService;
import com.lavy.redbook.note.biz.service.TopicService;
import com.lavy.redbook.user.api.dto.resp.FindUserByIdRspDTO;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记服务实现类
 */
@Service
@Slf4j
public class NoteServiceImpl extends ServiceImpl<NoteDOMapper, NoteDO> implements NoteService {

    @Resource
    private DistributedIdGeneratorRpcService distributedIdGeneratorRpcService;
    @Resource
    private KvRpcService kvRpcService;
    @Resource
    private UserRpcService userRpcService;
    @Resource
    private TopicService topicService;
    @Resource(name = "noteTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 笔记详情本地缓存
     */
    private static final Cache<Long, String> LOCAL_CACHE = Caffeine.newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();


    @Override
    public Response<?> publishNote(PublishNoteReqVO publishNoteReqVO) {
        Integer type = publishNoteReqVO.getType();
        NoteTypeEnum typeEnum = NoteTypeEnum.valueOf(type);
        if (typeEnum == null) {
            return Response.fail(ResponseCodeEnum.NOTE_TYPE_ERROR);
        }

        NoteDOBuilder builder = NoteDO.builder();
        switch (typeEnum) {
            case IMAGE_TEXT -> {
                List<String> imgUris = publishNoteReqVO.getImgUris();
                Preconditions.checkArgument(!CollectionUtils.isEmpty(imgUris), "笔记图片不能为空");
                Preconditions.checkArgument(imgUris.size() <= 9, "图片不能超过9张");
                builder = builder.imgUris(String.join(",", imgUris));
            }
            case VIDEO -> {
                String videoUri = publishNoteReqVO.getVideoUri();
                Preconditions.checkArgument(StringUtils.isNotBlank(videoUri), "笔记视频不能为空");
                builder = builder.videoUri(videoUri);
            }
            default -> {

            }
        }
        String snowflakeId = distributedIdGeneratorRpcService.getSnowflakeId();

        String content = publishNoteReqVO.getContent();
        if (StringUtils.isNotBlank(content)) {
            String contentId = UUID.randomUUID().toString();
            boolean saved = kvRpcService.saveNoteContent(contentId, content);
            if (!saved) {
                throw new BizException(ResponseCodeEnum.NOTE_PUBLISH_FAIL);
            }
            builder = builder.contentUuid(contentId).isContentEmpty(false);
        }

        Long topicId = publishNoteReqVO.getTopicId();
        if (topicId != null && topicId > 0) {
            TopicDO topicDO = topicService.getById(topicId);
            builder = builder.topicId(topicId)
                    .topicName(topicDO.getName());
        }
        Long userId = LoginUserContextHolder.getUserId();
        NoteDO noteDO = builder.title(publishNoteReqVO.getTitle())
                .id(Long.valueOf(snowflakeId))
                .isTop(Boolean.FALSE)
                .creatorId(userId)
                .type(typeEnum)
                .status(NoteStatusEnum.NORMAL)
                .visible(NoteVisibleEnum.PUBLIC)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        try {
            // 笔记入库存储
            this.baseMapper.insert(builder.build());
        } catch (Exception e) {
            log.error("==> 笔记存储失败", e);
            if (StringUtils.isNotBlank(noteDO.getContentUuid())) {
                kvRpcService.deleteNoteContent(noteDO.getContentUuid());
            }
        }
        return Response.success();
    }

    @Override
    public Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO) {
        Long noteId = findNoteDetailReqVO.getId();
        Long userId = LoginUserContextHolder.getUserId();

        // 先从本地缓存中查询
        String findNoteDetailRspVOStrLocalCache = LOCAL_CACHE.getIfPresent(noteId);
        if (StringUtils.isNotBlank(findNoteDetailRspVOStrLocalCache)) {
            FindNoteDetailRspVO findNoteDetailRspVO =
                    JsonUtils.parseObject(findNoteDetailRspVOStrLocalCache, FindNoteDetailRspVO.class);
            log.info("==> 命中了本地缓存；{}", findNoteDetailRspVOStrLocalCache);
            // 可见性校验
            checkNoteVisible(NoteVisibleEnum.getByCode(findNoteDetailRspVO.getVisible()), userId,
                    findNoteDetailRspVO.getCreatorId());
            return Response.success(findNoteDetailRspVO);
        }

        // 从 Redis 缓存中获取
        String noteDetailRedisKey = RedisKeyConstants.buildNoteDetailKey(noteId);
        String noteDetailJson = redisTemplate.opsForValue().get(noteDetailRedisKey);
        if (StringUtils.isNotBlank(noteDetailJson)) {
            FindNoteDetailRspVO findNoteDetailRspVO = JsonUtils.parseObject(noteDetailJson, FindNoteDetailRspVO.class);
            // 可见性校验
            if (Objects.nonNull(findNoteDetailRspVO)) {
                Integer visible = findNoteDetailRspVO.getVisible();
                checkNoteVisible(NoteVisibleEnum.getByCode(visible), userId, findNoteDetailRspVO.getCreatorId());
            }
            // 异步线程中将用户信息存入本地缓存
            threadPoolTaskExecutor.submit(() -> {
                // 写入本地缓存
                LOCAL_CACHE.put(noteId,
                        Objects.isNull(findNoteDetailRspVO) ? "null" : JsonUtils.toJsonString(findNoteDetailRspVO));
            });
            return Response.success(findNoteDetailRspVO);
        }

        NoteDO noteDO = this.baseMapper.selectByPrimaryKey(noteId);

        // 笔记不存在
        if (Objects.isNull(noteDO)) {
            threadPoolTaskExecutor.execute(() -> {
                // 防止缓存穿透，将空数据存入 Redis 缓存 (过期时间不宜设置过长)
                // 保底1分钟 + 随机秒数
                long expireSeconds = 60 + RandomUtil.randomInt(60);
                redisTemplate.opsForValue().set(noteDetailRedisKey, "null", expireSeconds, TimeUnit.SECONDS);
            });
            throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
        }

        NoteVisibleEnum visible = noteDO.getVisible();
        checkNoteVisible(visible, userId, noteDO.getCreatorId());

        // 用户信息
        FindUserByIdRspDTO creatorUser = userRpcService.findById(noteDO.getCreatorId());

        String content = null;
        if (Objects.equals(noteDO.getIsContentEmpty(), Boolean.FALSE)) {
            content = kvRpcService.getNoteContent(noteDO.getContentUuid());
        }
        List<String> imgUris = null;
        if (Objects.equals(noteDO.getType(), NoteTypeEnum.IMAGE_TEXT) && StringUtils.isNotEmpty(noteDO.getImgUris())) {
            imgUris = List.of(noteDO.getImgUris().split(","));
        }

        FindNoteDetailRspVO findNoteDetailRspVO = FindNoteDetailRspVO.builder()
                .id(noteDO.getId())
                .type(noteDO.getType().getCode())
                .title(noteDO.getTitle())
                .content(content)
                .imgUris(imgUris)
                .topicId(noteDO.getTopicId())
                .topicName(noteDO.getTopicName())
                .creatorId(noteDO.getCreatorId())
                .creatorName(creatorUser.getNickName())
                .avatar(creatorUser.getAvatar())
                .videoUri(noteDO.getVideoUri())
                .updateTime(noteDO.getUpdateTime())
                .visible(noteDO.getVisible().getCode())
                .build();
        // 异步线程中将笔记详情存入 Redis
        threadPoolTaskExecutor.submit(() -> {
            String noteDetailJson1 = JsonUtils.toJsonString(findNoteDetailRspVO);
            // 过期时间（保底1天 + 随机秒数，将缓存过期时间打散，防止同一时间大量缓存失效，导致数据库压力太大）
            long expireSeconds = 60 * 60 * 24 + RandomUtil.randomInt(60 * 60 * 24);
            redisTemplate.opsForValue().set(noteDetailRedisKey, noteDetailJson1, expireSeconds, TimeUnit.SECONDS);
        });
        return Response.success(findNoteDetailRspVO);
    }

    /**
     * 检查笔记可见性
     *
     * @param visible 笔记可见性
     * @param userId 用户 ID
     * @param creatorId 笔记创建者 ID
     */
    private void checkNoteVisible(NoteVisibleEnum visible, Long userId, Long creatorId) {
        if (Objects.equals(visible, NoteVisibleEnum.PRIVATE) && !Objects.equals(userId, creatorId)) {
            throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
        }
    }
}
