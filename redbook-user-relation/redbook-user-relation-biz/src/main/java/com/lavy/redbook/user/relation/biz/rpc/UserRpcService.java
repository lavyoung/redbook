package com.lavy.redbook.user.relation.biz.rpc;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.client.UserFeignClient;
import com.lavy.redbook.user.api.dto.req.FindUserByIdReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByIdRspDTO;
import com.lavy.redbook.user.api.dto.resp.UserInfoDTO;

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

    /**
     * 批量查询用户信息
     *
     * @param userIds 用户ID
     * @return 用户信息
     */
    public List<UserInfoDTO> findByIds(List<Long> userIds) {
        Response<List<UserInfoDTO>> response = userFeignClient.findByIds(userIds);
        if (!response.isSuccess() || Objects.isNull(response.getData()) || CollectionUtils.isEmpty(
                response.getData())) {
            return null;
        }
        return response.getData();
    }

}
