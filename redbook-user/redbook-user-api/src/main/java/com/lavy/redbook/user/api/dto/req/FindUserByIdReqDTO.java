package com.lavy.redbook.user.api.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/16
 * @version: v1.0.0
 * @description: 根据用户ID查询用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserByIdReqDTO {

    /**
     * 手机号
     */
    @NotNull(message = "用户 ID 不能为空")
    private Long id;

}