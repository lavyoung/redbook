package com.lavy.redbook.framework.biz.context.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.lavy.redbook.framework.biz.context.filter.HeaderUserId2ContextFilter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 添加用户ID到上下文过滤器
 */
@AutoConfiguration
public class ContextAutoConfiguration {


    /**
     * 添加用户ID到上下文过滤器
     *
     * @return 用户ID到上下文过滤器
     */
    @Bean
    public HeaderUserId2ContextFilter headerUserId2ContextFilter() {
        return new HeaderUserId2ContextFilter();
    }
}
