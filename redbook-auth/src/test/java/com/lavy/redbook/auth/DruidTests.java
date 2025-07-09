package com.lavy.redbook.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.druid.filter.config.ConfigTools;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-09
 */
@SpringBootTest
@Slf4j
public class DruidTests {


    /**
     * Druid 密码加密
     */
    @Test
    @SneakyThrows
    void testEncodePassword() {
        // 你的密码
        String password = "123456";
        String[] arr = ConfigTools.genKeyPair(512);

        // 私钥
        log.info("privateKey: {}", arr[0]);
        // 公钥
        log.info("publicKey: {}", arr[1]);

        // 通过私钥加密密码
        String encodePassword = ConfigTools.encrypt(arr[0], password);
        log.info("password: {}", encodePassword);
    }
}
