package com.lavy.redbook.user.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
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
}
