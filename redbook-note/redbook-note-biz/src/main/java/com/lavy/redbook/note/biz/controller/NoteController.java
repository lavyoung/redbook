package com.lavy.redbook.note.biz.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.note.api.vo.req.DeleteNoteReqVO;
import com.lavy.redbook.note.api.vo.req.FindNoteDetailReqVO;
import com.lavy.redbook.note.api.vo.req.PublishNoteReqVO;
import com.lavy.redbook.note.api.vo.req.TopNoteReqVO;
import com.lavy.redbook.note.api.vo.req.UpdateNoteReqVO;
import com.lavy.redbook.note.api.vo.req.UpdateNoteVisibleOnlyMeReqVO;
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

    /**
     * 笔记详情
     *
     * @param findNoteDetailReqVO 笔记 ID
     * @return 笔记详情
     */
    @PostMapping(value = "/detail")
    @ApiOperationLog(description = "笔记详情")
    public Response<FindNoteDetailRspVO> findNoteDetail(
            @Validated @RequestBody FindNoteDetailReqVO findNoteDetailReqVO) {
        return noteService.findNoteDetail(findNoteDetailReqVO);
    }

    /**
     * 笔记修改
     *
     * @param updateNoteReqVO 笔记修改参数
     * @return 响应结果
     */
    @PostMapping(value = "/update")
    @ApiOperationLog(description = "笔记修改")
    public Response<?> updateNote(@Validated @RequestBody UpdateNoteReqVO updateNoteReqVO) {
        return noteService.updateNote(updateNoteReqVO);
    }

    /**
     * 删除笔记
     *
     * @param deleteNoteReqVO 删除笔记参数
     * @return 响应结果
     */
    @PostMapping(value = "/delete")
    @ApiOperationLog(description = "删除笔记")
    public Response<?> deleteNote(@Validated @RequestBody DeleteNoteReqVO deleteNoteReqVO) {
        return noteService.deleteNote(deleteNoteReqVO);
    }

    /**
     * 笔记仅对自己可见
     *
     * @param updateNoteVisibleOnlyMeReqVO 笔记仅对自己可见参数
     * @return 响应结果
     */
    @PostMapping(value = "/visible/onlyme")
    @ApiOperationLog(description = "笔记仅对自己可见")
    public Response<?> visibleOnlyMe(
            @Validated @RequestBody UpdateNoteVisibleOnlyMeReqVO updateNoteVisibleOnlyMeReqVO) {
        return noteService.visibleOnlyMe(updateNoteVisibleOnlyMeReqVO);
    }

    /**
     * 置顶/取消置顶笔记
     *
     * @param topNoteReqVO 置顶/取消置顶笔记参数
     * @return 响应结果
     */
    @PostMapping(value = "/top")
    @ApiOperationLog(description = "置顶/取消置顶笔记")
    public Response<?> topNote(@Validated @RequestBody TopNoteReqVO topNoteReqVO) {
        return noteService.topNote(topNoteReqVO);
    }
}
