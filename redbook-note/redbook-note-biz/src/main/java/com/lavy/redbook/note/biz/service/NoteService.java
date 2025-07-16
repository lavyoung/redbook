package com.lavy.redbook.note.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.note.api.vo.req.PublishNoteReqVO;
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
}
