package com.lavy.redbook.oss.biz.service;

import org.springframework.web.multipart.MultipartFile;

import com.lavy.redbook.framework.common.response.Response;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 文件服务
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件上传结果
     */
    Response<?> uploadFile(MultipartFile file);
}
