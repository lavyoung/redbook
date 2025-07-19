package com.lavy.redbook.user.relation.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 用户关系服务启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lavy.redbook.user.relation.biz.domain.mapper")
public class RedbookUserRealtionApp {

    public static void main(String[] args) {
        SpringApplication.run(RedbookUserRealtionApp.class, args);
    }
}

