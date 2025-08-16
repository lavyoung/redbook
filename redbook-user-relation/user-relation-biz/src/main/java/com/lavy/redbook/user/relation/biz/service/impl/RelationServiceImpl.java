package com.lavy.redbook.user.relation.biz.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.DateUtils;
import com.lavy.redbook.framework.common.util.JsonUtils;
import com.lavy.redbook.framework.common.util.RandomUtils;
import com.lavy.redbook.user.api.dto.resp.FindUserByIdRspDTO;
import com.lavy.redbook.user.relation.api.req.dto.FollowUserMqDTO;
import com.lavy.redbook.user.relation.api.req.dto.UnfollowUserMqDTO;
import com.lavy.redbook.user.relation.api.req.vo.FollowUserReqVO;
import com.lavy.redbook.user.relation.api.req.vo.UnfollowUserReqVO;
import com.lavy.redbook.user.relation.biz.constant.MQConstants;
import com.lavy.redbook.user.relation.biz.constant.RedisKeyConstants;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FansDO;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FollowingDO;
import com.lavy.redbook.user.relation.biz.enums.LuaResultEnum;
import com.lavy.redbook.user.relation.biz.enums.ResponseCodeEnum;
import com.lavy.redbook.user.relation.biz.rpc.UserRpcService;
import com.lavy.redbook.user.relation.biz.service.FansService;
import com.lavy.redbook.user.relation.biz.service.FollowingService;
import com.lavy.redbook.user.relation.biz.service.RelationService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 关注业务实现类
 */
@Service
@Slf4j
public class RelationServiceImpl implements RelationService {


    @Resource
    private UserRpcService userRpcService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private FollowingService followingService;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private FansService fansService;

    /**
     * 关注用户
     *
     * @param followUserReqVO 关注用户请求参数
     * @return 响应
     */
    @Override
    public Response<?> follow(FollowUserReqVO followUserReqVO) {
        // 关注的用户 ID
        Long followUserId = followUserReqVO.getFollowUserId();

        // 当前登录的用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        // 校验：无法关注自己
        if (Objects.equals(userId, followUserId)) {
            throw new BizException(ResponseCodeEnum.CANT_FOLLOW_YOUR_SELF);
        }

        // 校验关注的用户是否存在
        FindUserByIdRspDTO findUserByIdRspDTO = userRpcService.findById(followUserId);

        if (Objects.isNull(findUserByIdRspDTO)) {
            throw new BizException(ResponseCodeEnum.FOLLOW_USER_NOT_EXISTED);
        }

        // 校验关注数是否已经达到上限
        // 构建当前用户关注列表的 Redis Key
        String followingRedisKey = RedisKeyConstants.buildUserFollowingKey(userId);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        // Lua 脚本路径
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/follow_check_and_add.lua")));
        // 返回值类型
        script.setResultType(Long.class);

        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 当前时间转时间戳
        long timestamp = DateUtils.localDateTime2Timestamp(now);

        // 执行 Lua 脚本，拿到返回结果
        Long result =
                redisTemplate.execute(script, Collections.singletonList(followingRedisKey), followUserId, timestamp);
        // 写入 Redis ZSET 关注列表
        LuaResultEnum luaResultEnum = LuaResultEnum.valueOf(result);

        if (Objects.isNull(luaResultEnum)) {
            log.error("## Lua 脚本执行结果异常，请检查 Lua 脚本");
            throw new BizException(ResponseCodeEnum.SYSTEM_ERROR);
        }

        // 判断返回结果
        switch (luaResultEnum) {
            // 关注数已达到上限
            case FOLLOW_LIMIT -> throw new BizException(ResponseCodeEnum.FOLLOWING_COUNT_LIMIT);
            // 已经关注了该用户
            case ALREADY_FOLLOWED -> throw new BizException(ResponseCodeEnum.ALREADY_FOLLOWED);
            // ZSet 关注列表不存在
            case ZSET_NOT_EXIST -> {
                List<FollowingDO> followingDOS = followingService.selectByUserId(userId);
                // 随机过期时间
                // 保底1天+随机秒数
                long expireSeconds = 60 * 60 * 24 + RandomUtils.randomNumber(60 * 60 * 24);
                // 若记录为空，直接 ZADD 关系数据, 并设置过期时间
                if (CollectionUtils.isEmpty(followingDOS)) {
                    DefaultRedisScript<Long> script2 = new DefaultRedisScript<>();
                    script2.setScriptSource(
                            new ResourceScriptSource(new ClassPathResource("/lua/follow_add_and_expire.lua")));
                    script2.setResultType(Long.class);

                    // TODO: 可以根据用户类型，设置不同的过期时间，若当前用户为大V, 则可以过期时间设置的长些或者不设置过期时间；如不是，则设置的短些
                    // 如何判断呢？可以从计数服务获取用户的粉丝数，目前计数服务还没创建，则暂时采用统一的过期策略
                    redisTemplate.execute(script2, Collections.singletonList(followingRedisKey), followUserId,
                            timestamp, expireSeconds);
                } else { // 若记录不为空，则将关注关系数据全量同步到 Redis 中，并设置过期时间；
                    Object[] luaArgs = buildLuaArgs(followingDOS, expireSeconds);

                    // 执行 Lua 脚本，批量同步关注关系数据到 Redis 中
                    DefaultRedisScript<Long> script3 = new DefaultRedisScript<>();
                    script3.setScriptSource(
                            new ResourceScriptSource(new ClassPathResource("/lua/follow_batch_add_and_expire.lua")));
                    script3.setResultType(Long.class);
                    redisTemplate.execute(script3, Collections.singletonList(followingRedisKey), luaArgs);

                    // 再次调用上面的 Lua 脚本：follow_check_and_add.lua , 将最新的关注关系添加进去
                    result = redisTemplate.execute(script, Collections.singletonList(followingRedisKey), followUserId,
                            timestamp);
                    // check Lua 脚本执行结果
                    checkLuaScriptResult(result);
                }
            }
        }

        // 发送 MQ
        FollowUserMqDTO followUserMqDTO = FollowUserMqDTO.builder()
                .userId(userId)
                .followUserId(followUserId)
                .createTime(now)
                .build();

        // 构建消息对象，并将 DTO 转成 Json 字符串设置到消息体中
        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(followUserMqDTO))
                .build();

        // 通过冒号连接, 可让 MQ 发送给主题 Topic 时，携带上标签 Tag
        String destination = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW + ":" + MQConstants.TAG_FOLLOW;

        log.info("==> 开始发送关注操作 MQ, 消息体: {}", followUserMqDTO);

        // 异步发送 MQ 消息，提升接口响应速度
        rocketMQTemplate.asyncSendOrderly(destination, message, String.valueOf(userId), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> MQ 发送异常: ", throwable);
            }
        });
        return Response.success();
    }

    @Override
    public Response<?> unfollow(UnfollowUserReqVO unfollowUserReqVO) {
        Long userId = LoginUserContextHolder.getUserId();
        if (userId == null) {
            return Response.fail(ResponseCodeEnum.LOGIN_NOT_VALID);
        }
        if (Objects.equals(userId, unfollowUserReqVO.getUnfollowUserId())) {
            return Response.fail(ResponseCodeEnum.UNFOLLOW_SELF);
        }
        FindUserByIdRspDTO unfollowUser = userRpcService.findById(unfollowUserReqVO.getUnfollowUserId());
        if (Objects.isNull(unfollowUser)) {
            return Response.fail(ResponseCodeEnum.UNFOLLOW_USER_NOT_EXISTED);
        }
        String followingKey = RedisKeyConstants.buildUserFollowingKey(userId);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/unfollow_check_and_delete.lua")));
        script.setResultType(Long.class);

        Long result = redisTemplate.execute(script, Collections.singletonList(followingKey),
                unfollowUserReqVO.getUnfollowUserId());
        // 校验Lua 脚本执行结果
        // 取关的用户不在关注列表中
        if (Objects.equals(result, LuaResultEnum.NOT_FOLLOWED.getCode())) {
            return Response.fail(ResponseCodeEnum.NOT_FOLLOWED);
        }
        // 无缓存
        if (Objects.equals(result, LuaResultEnum.ZSET_NOT_EXIST.getCode())) {
            List<FollowingDO> followingDOS = followingService.selectByUserId(userId);

            if (CollectionUtils.isEmpty(followingDOS)) {
                throw new BizException(ResponseCodeEnum.NOT_FOLLOWED);
            } else {
                // 构建lua参数
                Object[] luaArgs = buildLuaArgs(followingDOS, 60 * 60 * 24 + RandomUtils.randomNumber(60 * 60 * 24));
                DefaultRedisScript<Long> script2 = new DefaultRedisScript<>();
                script2.setScriptSource(
                        new ResourceScriptSource(new ClassPathResource("/lua/follow_batch_add_and_expire.lua")));
                script2.setResultType(Long.class);
                redisTemplate.execute(script2, Collections.singletonList(followingKey), luaArgs);

                // 再次判断是否存在关注关系，不存在则抛出异常
                result = redisTemplate.execute(script, Collections.singletonList(followingKey),
                        unfollowUserReqVO.getUnfollowUserId());
                if (Objects.equals(result, LuaResultEnum.NOT_FOLLOWED.getCode())) {
                    throw new BizException(ResponseCodeEnum.NOT_FOLLOWED);
                }
            }
        }

        // 发送 MQ 消息
        UnfollowUserMqDTO unfollowUserMqDTO = UnfollowUserMqDTO.builder()
                .userId(userId)
                .unfollowUserId(unfollowUserReqVO.getUnfollowUserId())
                .createTime(LocalDateTime.now())
                .build();

        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(unfollowUserMqDTO)).build();
        String dest = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW + ":" + MQConstants.TAG_UNFOLLOW;
        rocketMQTemplate.asyncSendOrderly(dest, message, String.valueOf(userId), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> MQ 发送成功, {}", unfollowUserMqDTO);
            }

            @Override
            public void onException(Throwable e) {
                log.error("==> MQ 发送失败：{}", unfollowUserMqDTO, e);
            }
        });
        return Response.success();
    }

    @Override
    public Response<?> getFans() {
        Long userId = LoginUserContextHolder.getUserId();
        if (userId == null || userId == 0) {
            throw new BizException(ResponseCodeEnum.LOGIN_NOT_VALID);
        }
        List<FansDO> fansDOS = fansService.getFans(userId);
        return Response.success(fansDOS);
    }

    /**
     * 校验 Lua 脚本结果，根据状态码抛出对应的业务异常
     */
    private static void checkLuaScriptResult(Long result) {
        LuaResultEnum luaResultEnum = LuaResultEnum.valueOf(result);

        if (Objects.isNull(luaResultEnum)) {
            throw new RuntimeException("Lua 返回结果错误");
        }
        // 校验 Lua 脚本执行结果
        switch (luaResultEnum) {
            // 关注数已达到上限
            case FOLLOW_LIMIT -> throw new BizException(ResponseCodeEnum.FOLLOWING_COUNT_LIMIT);
            // 已经关注了该用户
            case ALREADY_FOLLOWED -> throw new BizException(ResponseCodeEnum.ALREADY_FOLLOWED);
        }
    }

    /**
     * 构建 Lua 脚本参数
     *
     * @param followingDOS 关注关系列表
     * @param expireSeconds 过期时间
     */
    private static Object[] buildLuaArgs(List<FollowingDO> followingDOS, long expireSeconds) {
        // 每个关注关系有 2 个参数（score 和 value），再加一个过期时间
        int argsLength = followingDOS.size() * 2 + 1;
        Object[] luaArgs = new Object[argsLength];

        int i = 0;
        for (FollowingDO following : followingDOS) {
            // 关注时间作为 score
            luaArgs[i] = DateUtils.localDateTime2Timestamp(following.getCreateTime());
            // 关注的用户 ID 作为 ZSet value
            luaArgs[i + 1] = following.getFollowingUserId();
            i += 2;
        }
        // 最后一个参数是 ZSet 的过期时间
        luaArgs[argsLength - 1] = expireSeconds;
        return luaArgs;
    }
}
