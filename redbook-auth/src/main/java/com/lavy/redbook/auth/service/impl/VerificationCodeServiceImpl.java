package com.lavy.redbook.auth.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lavy.redbook.auth.constant.RedisKeyConstants;
import com.lavy.redbook.auth.enums.ResponseCodeEnum;
import com.lavy.redbook.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.lavy.redbook.auth.service.VerificationCodeService;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;

import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 发送短信验证码
     *
     * @param sendVerificationCodeReqVO 发送短信验证码请求参数
     * @return 响应结果
     */
    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        // todo 验证参数
        log.info("发送验证码请求参数：{}", sendVerificationCodeReqVO);
        String phone = sendVerificationCodeReqVO.getPhone();
        // 判断是否已发送
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);
        boolean isSent = redisTemplate.hasKey(key);
        if (isSent) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }
        // 生成验证码
        String numbers = RandomUtil.randomNumbers(6);
        // todo 调用第三方服务发送短信
        // 缓存验证码
        redisTemplate.opsForValue().set(key, numbers, 3, TimeUnit.MINUTES);
        return Response.success();
    }
}
