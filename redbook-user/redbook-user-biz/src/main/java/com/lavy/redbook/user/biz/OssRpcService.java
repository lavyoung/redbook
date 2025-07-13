package com.lavy.redbook.user.biz;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.oss.api.constant.OssFeignClient;

import jakarta.annotation.Resource;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: OSS服务RPC服务
 */
@Component
public class OssRpcService {

    @Resource
    private OssFeignClient ossFeignClient;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件地址
     */
    public String uploadFile(MultipartFile file) {
        // 调用对象存储服务上传文件
        Response<String> response = ossFeignClient.uploadFile(file);
        if (!response.isSuccess()) {
            return null;
        }
        // 返回图片访问链接
        return response.getData();
    }
}
