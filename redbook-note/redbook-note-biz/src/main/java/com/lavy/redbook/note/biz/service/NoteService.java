package com.lavy.redbook.note.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.note.api.vo.req.DeleteNoteReqVO;
import com.lavy.redbook.note.api.vo.req.FindNoteDetailReqVO;
import com.lavy.redbook.note.api.vo.req.PublishNoteReqVO;
import com.lavy.redbook.note.api.vo.req.TopNoteReqVO;
import com.lavy.redbook.note.api.vo.req.UpdateNoteReqVO;
import com.lavy.redbook.note.api.vo.req.UpdateNoteVisibleOnlyMeReqVO;
import com.lavy.redbook.note.api.vo.resp.FindNoteDetailRspVO;
import com.lavy.redbook.note.biz.domain.dataobject.NoteDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记服务
 */
public interface NoteService extends IService<NoteDO> {

    /**
     * 发布笔记
     *
     * @param publishNoteReqVO 发布笔记请求参数
     * @return 笔记发布结果
     */
    Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);

    /**
     * 查询笔记详情
     *
     * @param findNoteDetailReqVO 查询笔记详情请求参数
     * @return 笔记详情
     */
    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);

    /**
     * 更新笔记
     *
     * @param updateNoteReqVO 更新笔记请求参数
     * @return 更新结果
     */
    Response<?> updateNote(UpdateNoteReqVO updateNoteReqVO);

    /**
     * 删除笔记缓存
     *
     * @param noteId 笔记ID
     */
    void deleteNoteLocalCache(Long noteId);

    /**
     * 删除笔记
     *
     * @param deleteNoteReqVO 删除笔记请求参数
     * @return 删除结果
     */
    Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO);

    /**
     * 笔记仅自己可见
     *
     * @param updateNoteVisibleOnlyMeReqVO 笔记仅自己可见请求参数
     * @return 笔记仅自己可见结果
     */
    Response<?> visibleOnlyMe(UpdateNoteVisibleOnlyMeReqVO updateNoteVisibleOnlyMeReqVO);

    /**
     * 置顶笔记
     *
     * @param topNoteReqVO 置顶笔记请求参数
     * @return 置顶结果
     */
    Response<?> topNote(TopNoteReqVO topNoteReqVO);
}
