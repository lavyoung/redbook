package com.lavy.redbook.count.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/17
 * @version: v1.0.0
 * @description: 笔记计数服务启动类
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.lavy.redbook")

public class RedbookCountApp {

    public static void main(String[] args) {
        SpringApplication.run(RedbookCountApp.class, args);
    }
}
