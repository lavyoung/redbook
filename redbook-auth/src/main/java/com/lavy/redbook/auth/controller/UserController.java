package com.lavy.redbook.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.auth.model.vo.user.UserLoginReqVO;
import com.lavy.redbook.auth.service.UserService;
import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lavy.redbook.framework.common.constant.ApiHeaderConstant;
import com.lavy.redbook.framework.common.response.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: todo
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @PostMapping("/doLogin")
    @ApiOperationLog(description = "用户登录/注册")
    public Response<?> doLogin(@Validated @RequestBody UserLoginReqVO reqVO) {
        return userService.doLogin(reqVO);
    }

    @GetMapping("/logout")
    @ApiOperationLog(description = "用户登出")
    public Response<?> logout(@RequestHeader(value = ApiHeaderConstant.HEADER_USER_ID) String userId) {
        return userService.logout(userId);
    }
}
