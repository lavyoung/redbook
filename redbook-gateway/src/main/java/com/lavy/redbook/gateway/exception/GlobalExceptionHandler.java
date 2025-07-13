package com.lavy.redbook.gateway.exception;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.JsonUtils;
import com.lavy.redbook.gateway.enums.ResponseCodeEnum;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 全局异常处理
 */
@Component
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    /**
     * 异常处理
     *
     * @param exchange 请求
     * @param ex 异常
     * @return 异常处理结果
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 响应结果
        ServerHttpResponse response = exchange.getResponse();
        log.error("==> 全局异常处理", ex);
        Response<?> result;
        switch (ex) {
            case NotLoginException e -> {
                // 认证异常
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                result = Response.fail(ResponseCodeEnum.UNAUTHORIZED.getErrorCode(), e.getMessage());
            }
            case NotPermissionException e -> {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                result = Response.fail(ResponseCodeEnum.UNAUTHORIZED.getErrorCode(),
                        ResponseCodeEnum.UNAUTHORIZED.getErrorMessage());
            }
            default -> {
                result = Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
            }
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 返回结果
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            return dataBufferFactory.wrap(JsonUtils.toJsonString(result).getBytes(StandardCharsets.UTF_8));
        }));
    }
}
