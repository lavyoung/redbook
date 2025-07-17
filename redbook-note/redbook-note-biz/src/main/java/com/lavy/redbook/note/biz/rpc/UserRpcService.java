package com.lavy.redbook.note.biz.rpc;

import org.springframework.stereotype.Component;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.client.UserFeignClient;
import com.lavy.redbook.user.api.dto.req.FindUserByIdReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByIdRspDTO;

import jakarta.annotation.Resource;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/16
 * @version: v1.0.0
 * @description: user api
 */
@Component
public class UserRpcService {

    @Resource
    private UserFeignClient userFeignClient;

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public FindUserByIdRspDTO findById(Long userId) {
        Response<FindUserByIdRspDTO> response =
                userFeignClient.findById(FindUserByIdReqDTO.builder().id(userId).build());
        if (response.isSuccess()) {
            return response.getData();
        }
        return null;
    }
}
