package com.lavy.redbook.kv.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.kv.api.constant.ApiConstants;
import com.lavy.redbook.kv.api.dto.req.AddNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.DeleteNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.FindNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.resp.FindNoteContentRspDTO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: KvFeignClient
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, path = "")
public interface KvFeignClient {

    String PREFIX = "/kv";

    /**
     * 添加笔记内容
     *
     * @param addNoteContentReqDTO 请求参数
     * @return 响应结果
     */
    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(@RequestBody AddNoteContentReqDTO addNoteContentReqDTO);

    /**
     * 查询笔记内容
     *
     * @param findNoteContentReqDTO 笔记内容查询参数
     * @return 笔记内容
     */
    @PostMapping(value = PREFIX + "/note/content/find")
    Response<FindNoteContentRspDTO> findNoteContent(@RequestBody FindNoteContentReqDTO findNoteContentReqDTO);

    /**
     * 删除笔记内容
     *
     * @param deleteNoteContentReqDTO 笔记内容删除参数
     * @return 删除结果
     */
    @PostMapping(value = PREFIX + "/note/content/delete")
    Response<?> deleteNoteContent(@RequestBody DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
