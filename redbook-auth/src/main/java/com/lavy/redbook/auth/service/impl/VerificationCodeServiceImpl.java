package com.lavy.redbook.auth.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.lavy.redbook.auth.constant.RedisKeyConstants;
import com.lavy.redbook.auth.enums.ResponseCodeEnum;
import com.lavy.redbook.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.lavy.redbook.auth.service.VerificationCodeService;
import com.lavy.redbook.auth.third.sms.AliyunSmsHelper;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.RandomUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AliyunSmsHelper aliyunSmsHelper;
    @Autowired
    @Qualifier("authThreadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolExecutor;

    /**
     * 发送短信验证码
     *
     * @param sendVerificationCodeReqVO 发送短信验证码请求参数
     * @return 响应结果
     */
    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        //        if (sendVerificationCodeReqVO == null || sendVerificationCodeReqVO.getPhone() == null || PhoneUtil
        //        .isMobile(sendVerificationCodeReqVO.getPhone())) {
        //            throw new BizException(ResponseCodeEnum.PARAM_NOT_VALID);
        //        }
        log.info("发送验证码请求参数：{}", sendVerificationCodeReqVO);
        String phone = sendVerificationCodeReqVO.getPhone();
        // 判断是否已发送
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);
        boolean isSent = redisTemplate.hasKey(key);
        if (isSent) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }
        // 生成验证码 s
        String code = RandomUtils.numberGenerator(6);
        log.info("验证码：{}", code);
        //    todo 暂时注释   threadPoolExecutor.execute(() -> aliyunSmsHelper.sendSms(phone, numbers));
        // 缓存验证码
        redisTemplate.opsForValue().set(key, code, 15, TimeUnit.MINUTES);
        return Response.success();
    }
}
