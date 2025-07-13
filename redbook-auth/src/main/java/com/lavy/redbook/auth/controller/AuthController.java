package com.lavy.redbook.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.auth.model.vo.user.UpdatePasswordReqVO;
import com.lavy.redbook.auth.model.vo.user.UserLoginReqVO;
import com.lavy.redbook.auth.service.AuthService;
import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
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
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;


    @PostMapping("/doLogin")
    @ApiOperationLog(description = "用户登录/注册")
    public Response<?> doLogin(@Validated @RequestBody UserLoginReqVO reqVO) {
        return authService.doLogin(reqVO);
    }

    @GetMapping("/logout")
    @ApiOperationLog(description = "用户登出")
    public Response<?> logout() {
        return authService.logout();
    }

    @PutMapping("/pwd/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Validated @RequestBody UpdatePasswordReqVO reqVO) {
        return authService.updatePassword(reqVO);
    }
}
