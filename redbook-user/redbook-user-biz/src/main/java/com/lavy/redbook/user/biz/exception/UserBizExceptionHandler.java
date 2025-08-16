package com.lavy.redbook.user.biz.exception;

import java.util.Optional;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.biz.enums.ResponseCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 响应码枚举
 */
@ControllerAdvice
@Slf4j
public class UserBizExceptionHandler {

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
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request,
            Exception e) {
        // 获取参数校验错误信息
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        StringBuilder stringBuilder = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException e1) {
            // 获取参数校验错误信息
            BindingResult bindingResult = e1.getBindingResult();
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
        }
        if (e instanceof HandlerMethodValidationException e1) {
            for (ParameterValidationResult detail : e1.getAllValidationResults()) {
                if (!CollectionUtils.isEmpty(detail.getResolvableErrors())) {
                    detail.getResolvableErrors().forEach(error -> {
                        stringBuilder.append(detail.getMethodParameter().getParameterType().getSimpleName())
                                .append(": ")
                                .append(error.getDefaultMessage())
                                .append("; ");
                    });
                }
            }

        }
        // 构建返回参数
        String errorMessage = stringBuilder.toString();
        log.warn("{} request fail, error: ", request.getRequestURI(), e);
        log.warn("r", e);
        return Response.fail(errorCode, errorMessage);
    }

    /**
     * 参数校验异常处理
     *
     * @param e 参数校验异常
     * @param request 请求
     * @return 响应
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    public Response<Object> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(),
                ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode(), e.getMessage());
        return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode(), e.getMessage());
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
        log.warn("{} request fail, error", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }
}
