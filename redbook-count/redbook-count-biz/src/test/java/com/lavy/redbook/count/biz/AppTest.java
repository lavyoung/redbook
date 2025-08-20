package com.lavy.redbook.count.biz;

import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import com.lavy.redbook.count.biz.constant.MQConstants;
import com.lavy.redbook.framework.common.util.JsonUtils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/20
 * @version: v1.0.0
 * @description: todo
 */
@SpringBootTest
@Slf4j
public class AppTest {


    @Resource
    private RocketMQTemplate rocketMQTemplate;

    // 省略...

    /**
     * 测试：发送计数 MQ, 以统计粉丝数
     */
    @Test
    void testSendCountFollowUnfollowMQ() {
        // 循环发送 3200 条 MQ
        for (long i = 0; i < 3200; i++) {
            // 构建消息体 DTO
            Map<String, Object> messageBody = new HashMap<>();
            messageBody.put("userId", String.valueOf(i));
            messageBody.put("followUserId", String.valueOf(i + 1));
            messageBody.put("type", 1);

            // 构建消息对象，并将 DTO 转成 Json 字符串设置到消息体中
            org.springframework.messaging.Message<String> message =
                    MessageBuilder.withPayload(JsonUtils.toJsonString(messageBody))
                            .build();

            // 发送 MQ 通知计数服务：统计粉丝数
            rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_FANS, message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("==> 【计数服务：粉丝数】MQ 发送成功，SendResult: {}", sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("==> 【计数服务：粉丝数】MQ 发送异常: ", throwable);
                }
            });
        }

    }
}
