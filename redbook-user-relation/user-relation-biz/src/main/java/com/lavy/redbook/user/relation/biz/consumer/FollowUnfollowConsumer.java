package com.lavy.redbook.user.relation.biz.consumer;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

import org.apache.rocketmq.common.message.Message;
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
import com.lavy.redbook.user.relation.biz.constant.MQConstants;
import com.lavy.redbook.user.relation.biz.constant.RedisKeyConstants;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FansDO;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FollowingDO;
import com.lavy.redbook.user.relation.biz.domain.mapper.FansDOMapper;
import com.lavy.redbook.user.relation.biz.domain.mapper.FollowingDOMapper;
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
@RocketMQMessageListener(consumerGroup = Constants.CONSUMER_GROUP,
        topic = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW
)
@Slf4j
public class FollowUnfollowConsumer implements RocketMQListener<Message> {

    @Resource
    private FollowingDOMapper followingDOMapper;
    @Resource
    private FansDOMapper fansDOMapper;
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
                // TODO 取消关注
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

        if (bodyJsonStr != null) {
            return;
        }
        // 幂等性：通过联合唯一索引保证

        Long userId = followUserMqDTO.getUserId();
        Long followUserId = followUserMqDTO.getFollowUserId();
        LocalDateTime createTime = followUserMqDTO.getCreateTime();

        // 插入数据
        transactionContextHolder.executeTransaction(() -> {
            int count = followingDOMapper.insert(FollowingDO.builder()
                    .userId(userId)
                    .followingUserId(followUserId)
                    .createTime(createTime)
                    .build());

            // 粉丝表：一条记录
            if (count > 0) {
                fansDOMapper.insert(FansDO.builder()
                        .userId(followUserId)
                        .fansUserId(userId)
                        .createTime(createTime)
                        .build());
            }
            return true;
        });
        // 执行Redis 命令
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
