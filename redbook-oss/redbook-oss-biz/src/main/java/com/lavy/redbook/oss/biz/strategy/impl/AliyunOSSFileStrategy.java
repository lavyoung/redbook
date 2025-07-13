package com.lavy.redbook.oss.biz.strategy.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import com.lavy.redbook.oss.biz.config.OssProperties;
import com.lavy.redbook.oss.biz.strategy.FileStrategy;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 阿里云OSS文件上传策略
 */
@Slf4j
@AllArgsConstructor
public class AliyunOSSFileStrategy implements FileStrategy {

    private final OSS aliyunOSSClient;
    private final OssProperties oss;

    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file, String bucketName) {

        log.info("## 阿里云文件上传");
        if (file == null || file.getSize() == 0) {
            log.error("文件为空");
            throw new RuntimeException("文件为空");
        }
        String originalFilename = file.getOriginalFilename();
        String key = UUID.randomUUID().toString().replace("-", "");
        Assert.notNull(originalFilename, "文件名不能为空");
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = key + "." + suffix;

        PutObjectResult result = aliyunOSSClient.putObject(bucketName, fileName, file.getInputStream());
        log.debug("上传文件结果：{} ：{}", file.getOriginalFilename() + " ==> " + fileName, result);
        String url = aliyunOSSClient.generatePresignedUrl(bucketName, fileName, new Date(System.currentTimeMillis() +
                3600L * 1000 * 24 * 365)).toString();
        log.debug("阿里云文件上传 上传文件成功，url: {}", url);
        return url;
    }
}
