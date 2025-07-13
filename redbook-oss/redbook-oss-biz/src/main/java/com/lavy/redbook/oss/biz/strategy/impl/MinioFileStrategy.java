package com.lavy.redbook.oss.biz.strategy.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.lavy.redbook.oss.biz.config.OssProperties;
import com.lavy.redbook.oss.biz.strategy.FileStrategy;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: Minio文件上传策略
 */
@Slf4j
@AllArgsConstructor
public class MinioFileStrategy implements FileStrategy {

    private final MinioClient minioClient;

    private final OssProperties minio;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param bucketName 存储桶名称
     * @return 文件访问地址
     */
    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file, String bucketName) {
        log.debug("## Minio文件上传");
        if (file == null || file.getSize() == 0) {
            log.error("文件为空");
            throw new RuntimeException("文件为空");
        }
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        String key = UUID.randomUUID().toString().replace("-", "");
        Assert.notNull(originalFilename, "文件名不能为空");
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = key + "." + suffix;

        ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                .contentType(contentType)
                .stream(file.getInputStream(), file.getSize(), -1)
                .bucket(bucketName)
                .object(fileName).build());
        log.debug("上传文件结果：{} ：{}", file.getOriginalFilename() + " ==> " + fileName, response);
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .expiry(30, TimeUnit.DAYS)
                .method(Method.GET)
                .build());
        log.debug("上传文件成功，url: {}", url);
        return url;
    }
}
