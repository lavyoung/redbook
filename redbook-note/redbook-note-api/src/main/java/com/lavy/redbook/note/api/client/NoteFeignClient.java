package com.lavy.redbook.note.api.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.lavy.redbook.note.api.constant.ApiConstant;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description: 笔记服务
 */
@FeignClient(name = ApiConstant.SERVICE_NAME, path = "")
public interface NoteFeignClient {


}
