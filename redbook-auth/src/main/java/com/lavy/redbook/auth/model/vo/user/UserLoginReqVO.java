package com.lavy.redbook.auth.model.vo.user;

import com.lavy.redbook.framework.common.validator.PhoneNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户登录请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginReqVO {

    @PhoneNumber
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    /**
     * 验证码
     */
    private String code;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录类型  1： 手机号验证码 2： 账号密码
     */
    @NotNull(message = "登录类型不能为空")
    private Integer type;

}
