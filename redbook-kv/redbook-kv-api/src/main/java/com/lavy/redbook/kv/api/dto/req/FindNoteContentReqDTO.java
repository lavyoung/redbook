package com.lavy.redbook.kv.api.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 查询笔记内容请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteContentReqDTO {

    @NotBlank(message = "笔记 ID 不能为空")
    private String noteId;

    /**
     * 笔记内容
     */
    private String content;
}
