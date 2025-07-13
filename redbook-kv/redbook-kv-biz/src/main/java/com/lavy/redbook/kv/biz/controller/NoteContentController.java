package com.lavy.redbook.kv.biz.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.kv.api.dto.req.AddNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.DeleteNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.FindNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.resp.FindNoteContentRspDTO;
import com.lavy.redbook.kv.biz.service.NoteContentService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: todo
 */
@RestController
@RequestMapping("/kv")
@Slf4j
public class NoteContentController {

    @Resource
    private NoteContentService noteContentService;

    /**
     * 添加笔记内容
     *
     * @param addNoteContentReqDTO 请求参数
     * @return 响应结果
     */
    @PostMapping("/note/content/add")
    public Response<?> addNoteContent(@Validated @RequestBody AddNoteContentReqDTO addNoteContentReqDTO) {
        return noteContentService.addNoteContent(addNoteContentReqDTO);
    }

    /**
     * 查询笔记内容
     *
     * @param findNoteContentReqDTO 笔记内容查询参数
     * @return 笔记内容
     */
    @PostMapping(value = "/note/content/find")
    public Response<FindNoteContentRspDTO> findNoteContent(
            @Validated @RequestBody FindNoteContentReqDTO findNoteContentReqDTO) {
        return noteContentService.findNoteContent(findNoteContentReqDTO);
    }

    /**
     * 删除笔记内容
     *
     * @param deleteNoteContentReqDTO 笔记内容删除参数
     * @return 删除结果
     */
    @PostMapping(value = "/note/content/delete")
    public Response<?> deleteNoteContent(@Validated @RequestBody DeleteNoteContentReqDTO deleteNoteContentReqDTO) {
        return noteContentService.deleteNoteContent(deleteNoteContentReqDTO);
    }
}
