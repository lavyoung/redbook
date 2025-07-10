package com.lavy.redbook.auth.exception;

import java.util.Optional;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lavy.redbook.auth.enums.ResponseCodeEnum;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
@ControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param request 请求
     * @param bizException 业务异常
     * @return 错误信息
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Response<Object> handleException(HttpServletRequest request, BizException bizException) {
        log.warn("{} request fail, errorCode: {}, errorMessage:{}", request.getRequestURI(),
                bizException.getErrorCode(), bizException.getMessage());
        return Response.fail(bizException);
    }

    /**
     * 捕获参数校验异常
     *
     * @param request 请求
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request,
            MethodArgumentNotValidException e) {

        // 获取参数校验错误信息
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        // 获取参数校验错误信息
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder stringBuilder = new StringBuilder();
        Optional.of(bindingResult.getFieldErrors()).ifPresent(fieldErrors -> {
            fieldErrors.forEach(error -> {
                stringBuilder.append(error.getField())
                        .append(" ")
                        .append(error.getDefaultMessage())
                        .append(", 当前值")
                        .append(error.getRejectedValue())
                        .append("; ");
            });
        });
        // 构建返回参数
        String errorMessage = stringBuilder.toString();
        log.warn("{} request fail, errorCode: {}, errorMessage:{}", request.getRequestURI(), errorCode, errorMessage);
        log.warn("r", e);
        return Response.fail(errorCode, errorMessage);
    }

    /**
     * 捕获其他异常
     *
     * @param request 请求
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<Object> handleException(HttpServletRequest request, Exception e) {
        log.warn("{} request fail, errorCode: {}, errorMessage:{}", request.getRequestURI(),
                ResponseCodeEnum.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }
}
