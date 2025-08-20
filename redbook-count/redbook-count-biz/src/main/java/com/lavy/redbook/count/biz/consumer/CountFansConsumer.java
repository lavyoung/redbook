package com.lavy.redbook.count.biz.consumer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import com.github.phantomthief.collection.BufferTrigger;
import com.lavy.redbook.count.biz.constant.MQConstants;
import com.lavy.redbook.framework.common.constant.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/20
 * @version: v1.0.0
 * @description: 统计用户粉丝数
 */
@Component
@RocketMQMessageListener(consumerGroup = Constants.CONSUMER_GROUP_CLUSTERING + "_"
        + MQConstants.TOPIC_COUNT_FANS,
        topic = MQConstants.TOPIC_COUNT_FANS
)
@Slf4j
public class CountFansConsumer implements RocketMQListener<String> {

    private final BufferTrigger<String> bufferTrigger = BufferTrigger.<String> batchBlocking()
            .bufferSize(2000)
            .batchSize(1000)
            .linger(Duration.of(1000, ChronoUnit.MILLIS))
            .setConsumerEx(this::consumer)
            .build();


    @Override
    public void onMessage(String message) {
        log.info("## 统计用户粉丝数消费者消费成功, message: {}", message);
        bufferTrigger.enqueue(message);
    }

    private void consumer(List<String> messages) {
        log.info("## 聚合统计消费开始, message: {}", messages);
    }
}
