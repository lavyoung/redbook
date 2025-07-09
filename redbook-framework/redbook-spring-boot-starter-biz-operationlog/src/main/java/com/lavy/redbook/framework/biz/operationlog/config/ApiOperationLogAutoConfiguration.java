package com.lavy.redbook.framework.biz.operationlog.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLogAspect;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-09
 */
@AutoConfiguration
public class ApiOperationLogAutoConfiguration {

    @Bean
    public ApiOperationLogAspect apiOperationLogAspect() {
        return new ApiOperationLogAspect();
    }

}
