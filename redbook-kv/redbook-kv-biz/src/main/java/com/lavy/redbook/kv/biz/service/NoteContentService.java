package com.lavy.redbook.kv.biz.service;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.kv.api.dto.req.AddNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.DeleteNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.FindNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.resp.FindNoteContentRspDTO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 笔记内容服务
 */
public interface NoteContentService {

    /**
     * 添加笔记内容
     *
     * @param addNoteContentReqDTO 添加笔记内容请求参数
     * @return 添加笔记内容结果
     */
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);

    /**
     * 查询笔记内容
     *
     * @param findNoteContentReqDTO 查询笔记内容请求参数
     * @return 查询笔记内容结果
     */
    Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);

    /**
     * 删除笔记内容
     *
     * @param deleteNoteContentReqDTO 删除笔记内容请求参数
     * @return 删除笔记内容结果
     */
    Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);

}
