package com.lavy.redbook.oss.biz.factory;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSS;
import com.lavy.redbook.oss.biz.config.OssProperties;
import com.lavy.redbook.oss.biz.strategy.FileStrategy;
import com.lavy.redbook.oss.biz.strategy.impl.AliyunOSSFileStrategy;
import com.lavy.redbook.oss.biz.strategy.impl.MinioFileStrategy;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 文件上传策略工厂
 */
@Configuration
@Slf4j
@RefreshScope
public class FileStrategyFactory {

    @Resource
    private OssProperties ossProperties;
    @Resource
    private MinioClient minioClient;
    @Resource
    private OSS aliyunOSSClient;

    @Bean
    @RefreshScope
    public FileStrategy getFileStrategy() {
        return switch (ossProperties.getType()) {
            case ALIYUN -> {
                log.info("==> 使用阿里云文件上传策略");
                yield new AliyunOSSFileStrategy(aliyunOSSClient, ossProperties);
            }
            case MINIO -> {
                log.info("==> 使用Minio文件上传策略");
                yield new MinioFileStrategy(minioClient, ossProperties);
            }
            default -> throw new RuntimeException("暂不支持该存储类型");
        };
    }
}
