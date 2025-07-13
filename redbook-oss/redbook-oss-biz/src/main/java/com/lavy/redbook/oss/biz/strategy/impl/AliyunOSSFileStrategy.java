package com.lavy.redbook.oss.biz.strategy.impl;

import org.springframework.web.multipart.MultipartFile;

import com.lavy.redbook.oss.biz.strategy.FileStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 阿里云OSS文件上传策略
 */
@Slf4j
public class AliyunOSSFileStrategy implements FileStrategy {
    @Override
    public String uploadFile(MultipartFile file, String bucketName) {

        log.info("## 阿里云文件上传");
        return "";
    }
}
