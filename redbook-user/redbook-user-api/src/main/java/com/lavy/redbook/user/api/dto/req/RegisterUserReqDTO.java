package com.lavy.redbook.user.api.dto.req;

import com.lavy.redbook.framework.common.validator.PhoneNumber;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 注册用户请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserReqDTO {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phone;

}
