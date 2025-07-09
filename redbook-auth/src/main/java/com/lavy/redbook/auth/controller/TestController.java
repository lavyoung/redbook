package com.lavy.redbook.auth.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.auth.domain.dataobject.UserDO;
import com.lavy.redbook.auth.domain.mapper.UserDOMapper;
import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;

import lombok.RequiredArgsConstructor;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-09
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserDOMapper userMapper;

    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Object test() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "lavy");
        map.put("age", LocalDateTime.now());
        return map;
    }

    @GetMapping("/test2")
    public Object test1() {
        userMapper.insert(
                UserDO.builder().username("lavy").createTime(new Date()).updateTime(new Date()).build());
        return null;
    }
}
