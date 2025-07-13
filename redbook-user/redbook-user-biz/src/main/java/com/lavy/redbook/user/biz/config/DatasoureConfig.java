package com.lavy.redbook.user.biz.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: MyBatis 配置
 */
@MapperScan("com.lavy.redbook.user.biz.domain.mapper")
@Configuration
public class DatasoureConfig {
}
