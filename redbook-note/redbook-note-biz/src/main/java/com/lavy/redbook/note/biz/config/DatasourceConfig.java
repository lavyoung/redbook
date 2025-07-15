package com.lavy.redbook.note.biz.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description: 数据源配置
 */
@Configuration
@MapperScan("com.lavy.redbook.note.biz.domain.mapper")
public class DatasourceConfig {


}
