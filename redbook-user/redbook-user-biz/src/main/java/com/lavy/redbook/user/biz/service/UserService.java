package com.lavy.redbook.user.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.dto.req.FindUserByPhoneReqDTO;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
import com.lavy.redbook.user.api.dto.req.UpdateUserPasswordReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByPhoneRspDTO;
import com.lavy.redbook.user.biz.domain.dataobject.UserDO;
import com.lavy.redbook.user.biz.model.vo.UpdateUserInfoReqVO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 用户服务
 */
public interface UserService extends IService<UserDO> {

    /**
     * 更新用户信息
     *
     * @param updateUserInfoReqVO 更新用户信息请求参数
     * @return 更新结果
     */
    Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO);

    /**
     * 注册用户
     *
     * @param registerUserReqDTO 注册用户请求参数
     * @return 注册结果
     */
    Response<Long> register(RegisterUserReqDTO registerUserReqDTO);

    /**
     * 通过手机号查询用户信息
     *
     * @param findUserByPhoneReqDTO 查询用户信息请求参数
     * @return 用户信息
     */
    Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO);

    /**
     * 修改密码
     *
     * @param updateUserPasswordReqDTO 修改密码请求参数
     * @return 修改结果
     */
    Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO);

    /**
     * 推送用户角色
     *
     * @param userId 用户ID
     * @return 推送结果
     */
    Response<?> pushUserRoles(Long userId);

}
