package com.lavy.redbook.framework.biz.context.interceptor;

import java.util.Objects;

import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.constant.ApiHeaderConstant;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: FeignRequestInterceptor
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    /**
     * 添加请求头
     *
     * @param requestTemplate 请求模板
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = LoginUserContextHolder.getUserId();
        if (Objects.nonNull(userId)) {
            log.debug("########## feign 请求设置请求头 userId: {}", userId);
            requestTemplate.header(ApiHeaderConstant.HEADER_USER_ID, String.valueOf(userId));
        }
    }
}
