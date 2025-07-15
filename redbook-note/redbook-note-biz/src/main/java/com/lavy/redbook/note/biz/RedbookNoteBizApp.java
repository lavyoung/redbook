package com.lavy.redbook.note.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description: RedbookNoteBizApp
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.lavy.redbook")
public class RedbookNoteBizApp {

    public static void main(String[] args) {
        SpringApplication.run(RedbookNoteBizApp.class, args);
    }
}
