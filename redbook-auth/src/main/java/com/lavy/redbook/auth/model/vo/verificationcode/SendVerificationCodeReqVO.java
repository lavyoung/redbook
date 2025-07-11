package com.lavy.redbook.auth.model.vo.verificationcode;

import java.io.Serial;
import java.io.Serializable;

import com.lavy.redbook.framework.common.validator.PhoneNumber;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendVerificationCodeReqVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @PhoneNumber
    @NotBlank(message = "手机号码不能为空")
    private String phone;
}
