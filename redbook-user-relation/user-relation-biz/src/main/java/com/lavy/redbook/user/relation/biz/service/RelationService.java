package com.lavy.redbook.user.relation.biz.service;

import com.lavy.redbook.framework.common.response.PageResponse;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.relation.api.req.vo.FindFollowingListReqVO;
import com.lavy.redbook.user.relation.api.req.vo.FindFollowingUserRspVO;
import com.lavy.redbook.user.relation.api.req.vo.FollowUserReqVO;
import com.lavy.redbook.user.relation.api.req.vo.UnfollowUserReqVO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 关注服务
 */
public interface RelationService {

    /**
     * 关注用户
     *
     * @param followUserReqVO 关注用户请求参数
     * @return 响应
     */
    Response<?> follow(FollowUserReqVO followUserReqVO);

    /**
     * 取关用户
     *
     * @param unfollowUserReqVO 取关用户请求参数
     * @return 响应
     */
    Response<?> unfollow(UnfollowUserReqVO unfollowUserReqVO);

    /**
     * 查询关注列表
     *
     * @param findFollowingListReqVO 查询关注列表请求参数
     * @return 响应
     */
    PageResponse<FindFollowingUserRspVO> findFollowingList(FindFollowingListReqVO findFollowingListReqVO);

    /**
     * 获取粉丝
     *
     * @return 响应
     */
    Response<?> getFans();
}
