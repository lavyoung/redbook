package com.lavy.redbook.framework.common.response;

import java.io.Serializable;

import com.lavy.redbook.framework.common.exception.BaseExceptionInterface;
import com.lavy.redbook.framework.common.exception.BizException;

import lombok.Data;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-08
 */
@Data
public class Response<T> implements Serializable {

    // 是否成功
    private Boolean success = true;
    private String message;
    private String errorCode;
    private T data;

    // =================成功响应=======================
    public static <T> Response<T> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    // =================失败响应=======================
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> fail(String message, String errorCode) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setErrorCode(errorCode);
        return response;
    }

    public static <T> Response<T> fail(BizException bizException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(bizException.getErrorMessage());
        response.setErrorCode(bizException.getErrorCode());
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(baseException.getErrorCode());
        response.setMessage(baseException.getErrorMessage());
        return response;
    }
}
