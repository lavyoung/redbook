package com.lavy.redbook.jackson;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavy.redbook.jackson.config.JacksonConfig;


/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-09
 */
@AutoConfiguration
public class JacksonAutoConfiguration {


    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new JacksonConfig().jacksonMapper();
    }
}
