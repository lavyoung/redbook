package com.lavy.redbook.note.biz.rpc;

import org.springframework.stereotype.Component;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.kv.api.client.KvFeignClient;
import com.lavy.redbook.kv.api.dto.req.AddNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.DeleteNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.req.FindNoteContentReqDTO;
import com.lavy.redbook.kv.api.dto.resp.FindNoteContentRspDTO;

import jakarta.annotation.Resource;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: kv rpc服务
 */
@Component
public class KvRpcService {

    @Resource
    private KvFeignClient kvFeignClient;

    /**
     * 保存笔记内容
     *
     * @param uuid id
     * @param content 笔记内容
     * @return 保存结果
     */
    public boolean saveNoteContent(String uuid, String content) {
        AddNoteContentReqDTO addNoteContentReqDTO = AddNoteContentReqDTO.builder()
                .noteId(uuid)
                .content(content)
                .build();
        Response<?> response = kvFeignClient.addNoteContent(addNoteContentReqDTO);
        return response.isSuccess();
    }


    /**
     * 删除笔记内容
     *
     * @param uuid id
     * @return boolean
     */
    public boolean deleteNoteContent(String uuid) {
        DeleteNoteContentReqDTO deleteNoteContentReqDTO = DeleteNoteContentReqDTO.builder()
                .noteId(uuid)
                .build();
        return kvFeignClient.deleteNoteContent(deleteNoteContentReqDTO).isSuccess();
    }

    /**
     * 查询笔记内容
     *
     * @param uuid id
     * @return 笔记内容
     */
    public String getNoteContent(String uuid) {
        FindNoteContentReqDTO findNoteContentReqDTO = FindNoteContentReqDTO.builder()
                .noteId(uuid)
                .build();
        Response<FindNoteContentRspDTO> noteContent = kvFeignClient.findNoteContent(findNoteContentReqDTO);
        if (!noteContent.isSuccess()) {
            return null;
        }
        return noteContent.getData().getContent();
    }
}
