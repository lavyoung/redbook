package com.lavy.redbook.auth.alarm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lavy.redbook.auth.alarm.impl.MailAlarmHelper;
import com.lavy.redbook.auth.alarm.impl.SmsAlarmHelper;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 告警配置
 */
@Configuration
@RefreshScope
public class AlarmConfig {

    @Value("${alarm.type}")
    private String type;

    @Bean
    @RefreshScope
    public AlarmInterface alarmHelper() {
        // 根据配置文件中的告警类型，初始化选择不同的告警实现类
        switch (type) {
            case "sms" -> {
                return new SmsAlarmHelper();
            }
            case "mail" -> {
                return new MailAlarmHelper();
            }
            default -> {
                throw new IllegalArgumentException("错误的告警类型...");
            }
        }
    }
}
