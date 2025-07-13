package com.lavy.redbook.framework.biz.context.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.lavy.redbook.framework.biz.context.interceptor.FeignRequestInterceptor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: FeignContextAutoConfiguration
 */
@AutoConfiguration
public class FeignContextAutoConfiguration {

    /**
     * FeignRequestInterceptor
     *
     * @return FeignRequestInterceptor
     */
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
