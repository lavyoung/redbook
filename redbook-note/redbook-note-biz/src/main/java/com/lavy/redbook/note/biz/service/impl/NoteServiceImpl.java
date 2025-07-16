package com.lavy.redbook.note.biz.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.note.api.vo.req.PublishNoteReqVO;
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
import com.lavy.redbook.note.biz.service.NoteService;
import com.lavy.redbook.note.biz.service.TopicService;

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
    private TopicService topicService;

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
}
