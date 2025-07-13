package com.lavy.redbook.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-08
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.lavy.redbook")
public class RedbookAuthApp {

    public static void main(String[] args) {
        SpringApplication.run(RedbookAuthApp.class, args);
    }
}
