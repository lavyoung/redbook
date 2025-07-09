package com.lavy.redbook.framework.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-08
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    // 错误码
    private String errorCode;
    // 错误信息
    private String errorMessage;

    public BizException(BaseExceptionInterface baseException) {
        this.errorCode = baseException.getErrorCode();
        this.errorMessage = baseException.getErrorMessage();
    }
}
