package com.lavy.redbook.kv.biz.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.kv.api.dto.req.AddNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.DeleteNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.FindNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.resp.FindNoteContentRspDTO;
import com.lavy.redbook.kv.biz.domian.dataobject.NoteContentDO;
import com.lavy.redbook.kv.biz.domian.repository.NoteContentRepository;
import com.lavy.redbook.kv.biz.enums.ResponseCodeEnum;
import com.lavy.redbook.kv.biz.service.NoteContentService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 笔记内容服务实现类
 */
@Service
@Slf4j
public class NoteContentServiceImpl implements NoteContentService {

    @Resource
    private NoteContentRepository noteContentRepository;

    @Override
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO) {
        // 笔记 ID
        Long noteId = addNoteContentReqDTO.getNoteId();
        // 笔记内容
        String content = addNoteContentReqDTO.getContent();

        // 构建数据库 DO 实体类 // TODO: 暂时用 UUID, 目的是为了下一章讲解压测，不用动态传笔记 ID。后续改为笔记服务传过来的笔记 ID
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.randomUUID())
                .content(content)
                .build();

        // 插入数据
        noteContentRepository.save(nodeContent);

        return Response.success();
    }

    @Override
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO) {
        String noteId = findNoteContentReqDTO.getNoteId();
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString(noteId));
        if (optional.isEmpty()) {
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_NOT_FOUND);
        }
        NoteContentDO noteContentDO = optional.get();
        FindNoteContentRspDTO findNoteContentRspDTO = FindNoteContentRspDTO.builder()
                .noteId(noteContentDO.getId())
                .content(noteContentDO.getContent())
                .build();
        return Response.success(findNoteContentRspDTO);
    }

    @Override
    public Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO) {
        if (StringUtils.isBlank(deleteNoteContentReqDTO.getNoteId())) {
            return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID);
        }
        noteContentRepository.deleteById(UUID.fromString(deleteNoteContentReqDTO.getNoteId()));
        return Response.success();
    }
}
