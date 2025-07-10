package com.lavy.redbook.auth.constant;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
public class RedisKeyConstants {

    /**
     * 验证码 KEY 前缀
     */
    private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code:";

    /**
     * 构建验证码 KEY
     *
     * @param phone 手机号
     * @return 验证码 KEY
     */
    public static String buildVerificationCodeKey(String phone) {
        return VERIFICATION_CODE_KEY_PREFIX + phone;
    }
}
