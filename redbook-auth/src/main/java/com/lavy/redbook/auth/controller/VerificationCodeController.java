package com.lavy.redbook.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.lavy.redbook.auth.service.VerificationCodeService;
import com.lavy.redbook.framework.common.response.Response;

import lombok.RequiredArgsConstructor;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
@RestController
@RequestMapping("/verificationCode")
@RequiredArgsConstructor
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    /**
     * 发送验证码
     *
     * @param sendVerificationCodeReqVO 发送验证码请求参数
     * @return 响应结果
     */
    @PostMapping("/send")
    public Response<?> send(@Validated @RequestBody SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        return verificationCodeService.send(sendVerificationCodeReqVO);
    }
}
