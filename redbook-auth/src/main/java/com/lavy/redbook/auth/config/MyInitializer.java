package com.lavy.redbook.auth.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 初始化
 */
@Component
@Slf4j
public class MyInitializer {


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("监听");
    }
}
