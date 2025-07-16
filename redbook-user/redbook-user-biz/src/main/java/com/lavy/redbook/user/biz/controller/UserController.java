package com.lavy.redbook.user.biz.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.dto.req.FindUserByIdReqDTO;
import com.lavy.redbook.user.api.dto.req.FindUserByPhoneReqDTO;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
import com.lavy.redbook.user.api.dto.req.UpdateUserPasswordReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByIdRspDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByPhoneRspDTO;
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

    /**
     * 用户注册
     *
     * @param registerUserReqDTO 注册用户请求参数
     * @return 注册结果
     */
    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<Long> register(@Validated @RequestBody RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }

    /**
     * 手机号查询用户信息
     *
     * @param findUserByPhoneReqDTO 手机号查询用户信息请求参数
     * @return 用户信息
     */
    @PostMapping("/findByPhone")
    @ApiOperationLog(description = "手机号查询用户信息")
    public Response<FindUserByPhoneRspDTO> findByPhone(
            @Validated @RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO) {
        return userService.findByPhone(findUserByPhoneReqDTO);
    }

    /**
     * 密码更新
     */
    @PostMapping("/password/update")
    @ApiOperationLog(description = "密码更新")
    public Response<?> updatePassword(@Validated @RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO) {
        return userService.updatePassword(updateUserPasswordReqDTO);
    }

    /**
     * 推送用户角色
     */
    @GetMapping("/pushUserRoles")
    @ApiOperationLog(description = "推送用户角色")
    public Response<?> pushUserRoles(@RequestParam("userId") Long userId) {
        return userService.pushUserRoles(userId);
    }

    @PostMapping("/findById")
    @ApiOperationLog(description = "查询用户信息")
    public Response<FindUserByIdRspDTO> findById(@Validated @RequestBody FindUserByIdReqDTO findUserByIdReqDTO) {
        return userService.findById(findUserByIdReqDTO);
    }
}
