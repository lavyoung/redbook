package com.lavy.redbook.note.api.vo.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 删除笔记请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteNoteReqVO {

    @NotNull(message = "笔记 ID 不能为空")
    private Long id;

}
