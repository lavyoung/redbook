package com.lavy.redbook.framework.common.exception;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-08
 */
public interface BaseExceptionInterface {

    /**
     * @return 异常状态码
     */
    String getErrorCode();

    /**
     * @return 异常信息
     */
    String getErrorMessage();
}
