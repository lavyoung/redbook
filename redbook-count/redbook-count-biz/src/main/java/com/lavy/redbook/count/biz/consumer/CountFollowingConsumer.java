package com.lavy.redbook.count.biz.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import com.lavy.redbook.count.biz.constant.MQConstants;
import com.lavy.redbook.framework.common.constant.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/20
 * @version: v1.0.0
 * @description: 统计用户关注数
 */
@Component
@RocketMQMessageListener(consumerGroup = Constants.CONSUMER_GROUP_CLUSTERING + "_"
        + MQConstants.TOPIC_COUNT_FOLLOWING,
        topic = MQConstants.TOPIC_COUNT_FOLLOWING
)
@Slf4j
public class CountFollowingConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("## 统计用户关注数消费者消费成功, message: {}", message);
    }
}
