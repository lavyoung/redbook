package com.lavy.redbook.note.biz.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/17
 * @version: v1.0.0
 * @description: RocketMQ 配置
 */
@Configuration
@Import(RocketMQAutoConfiguration.class)
public class RocketMQConfig {


}
