package com.lavy.redbook.oss.api.constant;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.oss.api.client.ApiConstants;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 文件服务接口
 */
@FeignClient(value = ApiConstants.SERVICE_NAME)
public interface OssFeignClient {

    String PREFIX = "/file";

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件预览地址
     */
    @PostMapping(value = PREFIX + "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<String> uploadFile(@RequestPart(value = "file") MultipartFile file);
}
