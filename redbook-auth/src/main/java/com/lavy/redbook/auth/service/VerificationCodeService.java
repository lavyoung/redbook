package com.lavy.redbook.auth.service;

import com.lavy.redbook.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.lavy.redbook.framework.common.response.Response;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
public interface VerificationCodeService {

    /**
     * 发送短信验证码
     *
     * @param sendVerificationCodeReqVO 发送短信验证码请求参数
     * @return 发送结果
     */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);
}
