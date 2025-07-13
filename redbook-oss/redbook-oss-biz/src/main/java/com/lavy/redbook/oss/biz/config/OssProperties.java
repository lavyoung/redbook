package com.lavy.redbook.oss.biz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.lavy.redbook.oss.biz.enums.StorageType;

import lombok.Data;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: Minio配置
 */
@Data
@ConfigurationProperties(prefix = "storage")
public class OssProperties {

    private StorageType type;
    private Minio minio;
    private Aliyun aliyun;

    @Data
    public static class Minio {
        private String endpoint;
        private String accessKey;
        private String secretKey;
    }

    @Data
    public static class Aliyun {
        private String endpoint;
    }
}
