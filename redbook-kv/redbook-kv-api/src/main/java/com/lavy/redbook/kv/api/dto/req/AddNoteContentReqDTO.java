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
 * @description: 新增笔记内容请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddNoteContentReqDTO {

    @NotBlank(message = "笔记 ID 不能为空")
    private String noteId;

    @NotBlank(message = "笔记内容不能为空")
    private String content;

}