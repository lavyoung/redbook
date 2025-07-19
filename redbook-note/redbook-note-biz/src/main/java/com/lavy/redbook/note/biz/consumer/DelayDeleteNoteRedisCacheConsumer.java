package com.lavy.redbook.note.biz.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.lavy.redbook.note.biz.constant.MQConstants;
import com.lavy.redbook.note.biz.constant.RedisKeyConstants;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 删除笔记 Redis 缓存
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "redbook-group_"
        + MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE, topic = MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE)
public class DelayDeleteNoteRedisCacheConsumer implements RocketMQListener<String> {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(String body) {
        Long noteId = Long.valueOf(body);
        log.info("==> 延迟消息消费成功, 删除Redis缓存 noteId: {}", noteId);
        // 删除 Redis 笔记缓存
        String noteDetailRedisKey = RedisKeyConstants.buildNoteDetailKey(noteId);
        redisTemplate.delete(noteDetailRedisKey);
    }
}
