package com.lavy.redbook.user.relation.biz.consumer;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.lavy.redbook.framework.common.constant.Constants;
import com.lavy.redbook.framework.common.util.DateUtils;
import com.lavy.redbook.framework.common.util.JsonUtils;
import com.lavy.redbook.user.relation.api.req.dto.FollowUserMqDTO;
import com.lavy.redbook.user.relation.api.req.dto.UnfollowUserMqDTO;
import com.lavy.redbook.user.relation.biz.constant.MQConstants;
import com.lavy.redbook.user.relation.biz.constant.RedisKeyConstants;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FansDO;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FollowingDO;
import com.lavy.redbook.user.relation.biz.service.FansService;
import com.lavy.redbook.user.relation.biz.service.FollowingService;
import com.lavy.redbook.user.relation.biz.template.TransactionContextHolder;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 笔记服务消费者
 */
@Component
@RocketMQMessageListener(consumerGroup = Constants.CONSUMER_GROUP_CLUSTERING + "_"
        + MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW,
        topic = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW,
        consumeMode = ConsumeMode.ORDERLY
)
@Slf4j
public class FollowUnfollowConsumer implements RocketMQListener<Message> {

    @Resource
    private FollowingService followingService;
    @Resource
    private FansService fansService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    // 每秒创建 5000 个令牌
    @Resource
    private RateLimiter rateLimiter;
    @Resource
    private TransactionContextHolder transactionContextHolder;


    @Override
    public void onMessage(Message message) {
        rateLimiter.acquire();
        // 消息体
        String bodyJsonStr = new String(message.getBody());
        // 标签
        String tags = message.getTags();

        log.info("==> FollowUnfollowConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);
        // 关注
        switch (tags) {
            case MQConstants.TAG_FOLLOW:
                handleFollowTagMessage(bodyJsonStr);
                break;
            case MQConstants.TAG_UNFOLLOW:
                handleUnfollowTagMessage(bodyJsonStr);
                break;
        }
    }


    /**
     * 关注
     *
     * @param bodyJsonStr 消息体
     */
    private void handleFollowTagMessage(String bodyJsonStr) {
        // 将消息体 Json 字符串转为 DTO 对象
        FollowUserMqDTO followUserMqDTO = JsonUtils.parseObject(bodyJsonStr, FollowUserMqDTO.class);

        // 判空
        if (Objects.isNull(followUserMqDTO)) {
            return;
        }
        // 幂等性：通过联合唯一索引保证
        Long userId = followUserMqDTO.getUserId();
        Long followUserId = followUserMqDTO.getFollowUserId();
        LocalDateTime createTime = followUserMqDTO.getCreateTime();

        // 插入数据
        Boolean isSuccess = transactionContextHolder.executeTransaction(() -> {
            boolean success = followingService.save(FollowingDO.builder()
                    .userId(userId)
                    .followingUserId(followUserId)
                    .createTime(createTime)
                    .build());

            // 粉丝表：一条记录
            if (success) {
                fansService.save(FansDO.builder()
                        .userId(followUserId)
                        .fansUserId(userId)
                        .createTime(createTime)
                        .build());
            }
            return success;
        });
        if (Boolean.TRUE.equals(isSuccess)) {
            // 执行Redis 命令 如果粉丝列表存在缓存且未添加则添加
            DefaultRedisScript<Long> script = new DefaultRedisScript<>();
            script.setScriptSource(
                    new ResourceScriptSource(new ClassPathResource("/lua/follow_check_and_update_fans_zset.lua")));
            script.setResultType(Long.class);

            long timestamp = DateUtils.localDateTime2Timestamp(createTime);
            // 构建被关注用户的粉丝列表
            String key = RedisKeyConstants.buildUserFansKey(followUserId);
            redisTemplate.execute(script, Collections.singletonList(key), userId, timestamp);
        }
    }

    /**
     * 取关
     *
     * @param bodyJsonStr 消息内容
     */
    private void handleUnfollowTagMessage(String bodyJsonStr) {
        // 转换对象
        UnfollowUserMqDTO unfollowUserMqDTO = JsonUtils.parseObject(bodyJsonStr, UnfollowUserMqDTO.class);
        // 判空
        if (Objects.isNull(unfollowUserMqDTO)) {
            return;
        }
        Long userId = unfollowUserMqDTO.getUserId();
        Long unfollowUserId = unfollowUserMqDTO.getUnfollowUserId();
        // 编程式提交事务
        Boolean isSuccess = transactionContextHolder.executeTransaction(() -> {
            int c = followingService.deleteFollowing(userId, unfollowUserId);
            if (c > 0) {
                fansService.deleteFans(unfollowUserId, userId);
            }
            return c > 0;
        }, "删除粉丝数据");

        // 若数据库删除成功，更新 Redis，将自己从被取关用户的 ZSet 粉丝列表删除
        if (Boolean.TRUE.equals(isSuccess)) {
            String userFansKey = RedisKeyConstants.buildUserFansKey(unfollowUserId);
            redisTemplate.opsForZSet().remove(userFansKey, userId);
        }
    }

}
