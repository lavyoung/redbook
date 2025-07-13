package com.lavy.redbook.framework.common.util;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-08
 */
public class JsonUtils {

    private static ObjectMapper OBJECT_MAPPER;

    /**
     * 初始化：统一使用 Spring Boot 个性化配置的 ObjectMapper
     */
    public static void init(ObjectMapper objectMapper) {
        OBJECT_MAPPER = objectMapper;
    }

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param obj 对象
     * @return JSON 字符串
     */
    @SneakyThrows
    public static String toJsonString(Object obj) {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * 将 JSON 字符串转换为对象
     *
     * @param json JSON 字符串
     * @param clazz 对象类型
     * @return 对象
     */
    @SneakyThrows
    public static <T> T parseObject(String json, Class<T> clazz) {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    /**
     * 将 JSON 节点转换为对象
     *
     * @param json JSON 节点
     * @param clazz 节点类型
     * @return 节点对象
     */
    @SneakyThrows
    public static <T> List<T> parseListObject(Object json, Class<T> clazz) {
        if (json == null) {
            return Collections.emptyList();
        }
        JavaType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        if (json instanceof String) {
            return OBJECT_MAPPER.readValue((String) json, listType);
        } else {
            String jsonString = OBJECT_MAPPER.writeValueAsString(json);
            return OBJECT_MAPPER.readValue(jsonString, listType);
        }
    }
}
