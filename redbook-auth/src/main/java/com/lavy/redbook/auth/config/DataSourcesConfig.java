package com.lavy.redbook.auth.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-09
 */
@Configuration
@MapperScan("com.lavy.redbook.auth.domain.mapper")
public class DataSourcesConfig {

}
