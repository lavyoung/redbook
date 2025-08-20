package com.lavy.redbook.user.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.constant.ApiConstants;
import com.lavy.redbook.user.api.dto.req.FindUserByIdReqDTO;
import com.lavy.redbook.user.api.dto.req.FindUserByPhoneReqDTO;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
import com.lavy.redbook.user.api.dto.req.UpdateUserPasswordReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByIdRspDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByPhoneRspDTO;
import com.lavy.redbook.user.api.dto.resp.UserInfoDTO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 用户服务Feign客户端
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, path = "")
public interface UserFeignClient {

    String PREFIX = "/user";

    /**
     * 用户注册
     *
     * @param registerUserReqDTO 注册参数
     * @return 注册成功后的用户 ID
     */
    @PostMapping(value = PREFIX + "/register")
    Response<Long> registerUser(@RequestBody RegisterUserReqDTO registerUserReqDTO);

    /**
     * 根据手机号查询用户信息
     *
     * @param findUserByPhoneReqDTO 查询参数
     * @return 用户信息
     */
    @PostMapping(value = PREFIX + "/findByPhone")
    Response<FindUserByPhoneRspDTO> findByPhone(@RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO);

    /**
     * 密码更新
     *
     * @param updateUserPasswordReqDTO 更新参数
     * @return 无
     */
    @PostMapping(value = PREFIX + "/password/update")
    Response<?> updatePassword(@RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO);

    /**
     * 推送用户角色
     *
     * @return 无
     */
    @GetMapping(PREFIX + "/pushUserRoles")
    Response<?> pushUserRoles(@RequestParam("userId") Long userId);

    /**
     * 根据用户 ID 查询用户信息
     *
     * @param findUserByIdReqDTO 查询参数 用户ID
     * @return 用户信息
     */
    @PostMapping(value = PREFIX + "/findById")
    Response<FindUserByIdRspDTO> findById(@RequestBody FindUserByIdReqDTO findUserByIdReqDTO);

    /**
     * 根据用户 ID 批量查询用户信息
     *
     * @param userIds 用户ID
     * @return 用户信息
     */
    @PostMapping(PREFIX + "/findByIds")
    Response<List<UserInfoDTO>> findByIds(@RequestBody List<Long> userIds);
}
