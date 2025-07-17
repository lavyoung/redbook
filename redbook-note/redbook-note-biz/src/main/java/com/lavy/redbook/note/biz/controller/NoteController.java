package com.lavy.redbook.note.biz.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.note.api.vo.req.FindNoteDetailReqVO;
import com.lavy.redbook.note.api.vo.req.PublishNoteReqVO;
import com.lavy.redbook.note.api.vo.resp.FindNoteDetailRspVO;
import com.lavy.redbook.note.biz.service.NoteService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记
 */
@RestController
@RequestMapping("/note")
@Slf4j
public class NoteController {

    @Resource
    private NoteService noteService;

    /**
     * 发布笔记
     *
     * @param publishNoteReqVO 请求参数
     * @return 响应结果
     */
    @RequestMapping("/publish")
    @ApiOperationLog(description = "发布笔记")
    public Response<?> publishNote(@RequestBody PublishNoteReqVO publishNoteReqVO) {
        return noteService.publishNote(publishNoteReqVO);
    }

    @PostMapping(value = "/detail")
    @ApiOperationLog(description = "笔记详情")
    public Response<FindNoteDetailRspVO> findNoteDetail(
            @Validated @RequestBody FindNoteDetailReqVO findNoteDetailReqVO) {
        return noteService.findNoteDetail(findNoteDetailReqVO);
    }
}
