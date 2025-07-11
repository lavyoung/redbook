package com.lavy.redbook.auth.third.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;

import lombok.Data;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
@Configuration
@Data
public class AliyunSmsConfig {

    private String endpoint = "dysmsapi.aliyuncs.com";
    private String signName = "young的听听音乐";
    private String templateCode = "SMS_490645091";

    /**
     * 创建客户端
     */
    @Bean
    public Client aliyunSmsClient() throws Exception {
        Config config = new Config()
                // 配置 AccessKey ID，请确保代码运行环境配置了环境变量 。
                .setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                // 配置 AccessKey Secret，请确保代码运行环境配置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        // 配置 Endpoint。中国站请使用dysmsapi.aliyuncs.com
        config.endpoint = this.endpoint;
        return new Client(config);
    }
}
