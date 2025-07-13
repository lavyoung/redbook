package com.lavy.redbook.auth.rpc;

import org.springframework.stereotype.Component;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.client.UserFeignClient;
import com.lavy.redbook.user.api.dto.req.FindUserByPhoneReqDTO;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByPhoneRspDTO;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 用户服务RPC服务
 */
@Component
@Slf4j
public class UserRpcService {

    @Resource
    private UserFeignClient userFeignClient;

    /**
     * 注册用户
     *
     * @param phone 手机号
     * @return 用户ID
     */
    public Long registerUser(String phone) {
        RegisterUserReqDTO registerUserReqDTO = new RegisterUserReqDTO();
        registerUserReqDTO.setPhone(phone);

        Response<Long> response = userFeignClient.registerUser(registerUserReqDTO);

        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param phone 手机号
     * @return 用户信息
     */
    public FindUserByPhoneRspDTO findUserByPhone(String phone) {
        FindUserByPhoneReqDTO findUserByPhoneReqDTO = new FindUserByPhoneReqDTO();
        findUserByPhoneReqDTO.setPhone(phone);
        Response<FindUserByPhoneRspDTO> response = userFeignClient.findByPhone(findUserByPhoneReqDTO);
        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

}
