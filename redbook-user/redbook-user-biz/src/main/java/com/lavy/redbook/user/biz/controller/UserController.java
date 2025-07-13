package com.lavy.redbook.user.biz.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
import com.lavy.redbook.user.biz.model.vo.UpdateUserInfoReqVO;
import com.lavy.redbook.user.biz.service.UserService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 用户控制器
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户信息修改
     *
     * @param updateUserInfoReqVO 修改用户信息请求参数
     * @return 修改结果
     */
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> updateUserInfo(@Validated UpdateUserInfoReqVO updateUserInfoReqVO) {
        return userService.updateUserInfo(updateUserInfoReqVO);
    }


    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<Long> register(@Validated @RequestBody RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }
}
