package com.lavy.redbook.oss.biz.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;

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

    /**
     * 构建 阿里云 OSS 客户端
     */
    @Bean
    @RefreshScope
    public OSS aliyunOSSClient(OssProperties oss) {
        // 设置访问凭证
        DefaultCredentialProvider credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider(
                System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"), System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));

        // 创建 OSSClient 实例
        return new OSSClientBuilder().build(oss.getAliyun().getEndpoint(), credentialsProvider);
    }
}
