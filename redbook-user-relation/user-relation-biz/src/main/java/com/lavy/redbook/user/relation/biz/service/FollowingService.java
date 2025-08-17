package com.lavy.redbook.user.relation.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.framework.common.response.PageResponse;
import com.lavy.redbook.user.relation.api.req.dto.FollowingPageReq;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FollowingDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 关注服务
 */
public interface FollowingService extends IService<FollowingDO> {

    /**
     * 根据用户 ID 查询用户所关注的用户列表
     *
     * @param userId 用户 ID
     * @return 用户所关注的用户列表
     */
    List<FollowingDO> selectByUserId(Long userId);

    /**
     * 删除用户所关注的用户
     *
     * @param userId 用户 ID
     * @param followingId 关注的用户 ID
     * @return 删除结果
     */
    int deleteFollowing(Long userId, Long followingId);

    /**
     * 根据用户 ID 统计用户所关注的用户数量
     *
     * @param userId 用户 ID
     * @return 用户所关注的用户数量
     */
    long countByUserId(Long userId);

    /**
     * 分页查询用户所关注的用户列表
     *
     * @param pageReq 分页请求参数
     * @return 用户所关注的用户列表
     */
    PageResponse<FollowingDO> pageDO(FollowingPageReq pageReq);
}
