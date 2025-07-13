package com.lavy.redbook.gateway.enums;

import com.lavy.redbook.framework.common.exception.BaseExceptionInterface;

import lombok.AllArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 响应码枚举
 */
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("500", "系统繁忙，请稍后再试"),
    UNAUTHORIZED("401", "权限不足"),

    // ----------- 业务异常状态码 -----------
    NOT_FOUND("404", "路径错误"),

    ;

    private final String errorCode;
    private final String errorMessage;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
