package com.lavy.redbook.oss.biz.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 存储配置
 */
@Configuration
public class StorageConfig {

    @Bean
    @RefreshScope
    public OssProperties ossProperties() {
        return new OssProperties();
    }


    @Bean
    @RefreshScope
    public MinioClient minioClient(OssProperties oss) {
        return MinioClient.builder()
                .endpoint(oss.getMinio().getEndpoint())
                .credentials(oss.getMinio().getAccessKey(), oss.getMinio().getSecretKey())
                .build();
    }
}
