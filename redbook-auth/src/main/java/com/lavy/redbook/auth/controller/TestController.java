package com.lavy.redbook.auth.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.auth.domain.dataobject.UserDO;
import com.lavy.redbook.auth.domain.mapper.UserDOMapper;
import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lavy.redbook.framework.common.response.Response;

import cn.dev33.satoken.stp.StpUtil;
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
                UserDO.builder().username("lavy").createTime(LocalDateTime.now()).updateTime(LocalDateTime.now())
                        .build());
        return null;
    }

    @PostMapping("/test3")
    @ApiOperationLog(description = "测试接口2")
    public Response<UserDO> test2(@RequestBody UserDO user) {
        return Response.success(user);
    }

    // 测试登录，浏览器访问： http://localhost:8080/user/doLogin?username=zhang&password=123456
    @RequestMapping("/user/doLogin")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8080/user/isLogin
    @RequestMapping("/user/isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }
}
