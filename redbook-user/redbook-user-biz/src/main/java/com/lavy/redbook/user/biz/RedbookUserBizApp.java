package com.lavy.redbook.user.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: RedbookUserApp
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.lavy.redbook")
public class RedbookUserBizApp {

    public static void main(String[] args) {
        SpringApplication.run(RedbookUserBizApp.class, args);
    }
}
