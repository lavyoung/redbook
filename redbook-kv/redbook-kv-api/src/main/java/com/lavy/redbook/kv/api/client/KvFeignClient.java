package com.lavy.redbook.kv.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.kv.api.constant.ApiConstants;
import com.lavy.redbook.kv.api.dto.req.AddNoteContentReqDTO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: KvFeignClient
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, path = "")
public interface KvFeignClient {

    String PREFIX = "/kv";

    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(@RequestBody AddNoteContentReqDTO addNoteContentReqDTO);
}
