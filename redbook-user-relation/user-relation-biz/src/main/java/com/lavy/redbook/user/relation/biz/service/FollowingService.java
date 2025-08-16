package com.lavy.redbook.user.relation.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
