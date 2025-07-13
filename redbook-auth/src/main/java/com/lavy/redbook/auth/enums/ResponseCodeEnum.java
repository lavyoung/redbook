package com.lavy.redbook.auth.enums;

import com.lavy.redbook.framework.common.exception.BaseExceptionInterface;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/10
 * @version: v1.0.0
 * @description: 角色DOMapper
 */
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("AUTH-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("AUTH-10001", "参数错误"),

    // ----------- 业务异常状态码 -----------
    VERIFICATION_CODE_SEND_FREQUENTLY("AUTH-20000", "请求太频繁，请3分钟后再试"),
    VERIFICATION_CODE_ERROR("AUTH-20001", "验证码错误"),
    USER_NOT_EXIST("AUTH-20002", "用户不存在"),
    USER_EXIST("AUTH-20003", "用户已存在"),
    USER_PASSWORD_ERROR("AUTH-20004", "用户密码错误"),
    VERIFICATION_CODE_NOT_EXIST("AUTH-20005", "验证码失效"),
    LOGIN_TYPE_ERROR("AUTH-20006", "登录类型错误"),
    PHONE_OR_PASSWORD_ERROR("AUTH-20007", "手机号或密码错误"),
    ;

    private final String errorCode;
    private final String errorMessage;

    ResponseCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
