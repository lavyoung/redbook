package com.lavy.redbook.user.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.constant.ApiConstants;
import com.lavy.redbook.user.api.dto.req.FindUserByPhoneReqDTO;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByPhoneRspDTO;

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

}
