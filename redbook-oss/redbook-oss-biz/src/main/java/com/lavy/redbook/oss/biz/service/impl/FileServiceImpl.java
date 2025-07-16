package com.lavy.redbook.oss.biz.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.oss.biz.service.FileService;
import com.lavy.redbook.oss.biz.strategy.FileStrategy;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 文件服务实现类
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private FileStrategy fileStrategy;

    @Override
    public Response<String> uploadFile(MultipartFile file) {
        return Response.success(fileStrategy.uploadFile(file, "redbook-user"));
    }
}
