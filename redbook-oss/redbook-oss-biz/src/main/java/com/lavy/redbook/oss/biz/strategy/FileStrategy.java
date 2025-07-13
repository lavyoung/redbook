package com.lavy.redbook.oss.biz.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 文件上传策略
 */
public interface FileStrategy {


    /**
     * 文件上传
     *
     * @param file 文件
     * @param bucketName 存储桶名称
     * @return 文件上传路径
     */
    String uploadFile(MultipartFile file, String bucketName);
}
