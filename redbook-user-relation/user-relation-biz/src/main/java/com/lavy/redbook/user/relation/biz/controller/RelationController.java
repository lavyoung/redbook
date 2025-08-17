package com.lavy.redbook.user.relation.biz.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lavy.redbook.framework.common.response.PageResponse;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.relation.api.req.vo.FindFollowingListReqVO;
import com.lavy.redbook.user.relation.api.req.vo.FindFollowingUserRspVO;
import com.lavy.redbook.user.relation.api.req.vo.FollowUserReqVO;
import com.lavy.redbook.user.relation.api.req.vo.UnfollowUserReqVO;
import com.lavy.redbook.user.relation.biz.enums.ResponseCodeEnum;
import com.lavy.redbook.user.relation.biz.service.RelationService;

import jakarta.annotation.Resource;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 关注关系控制层
 */
@RestController
@RequestMapping("/relation")
public class RelationController {

    @Resource
    private RelationService relationService;

    /**
     * 关注用户
     *
     * @param followUserReqVO 关注用户请求参数
     * @return 响应结果
     */
    @PostMapping("/follow")
    @ApiOperationLog(description = "关注用户")
    public Response<?> follow(@Validated @RequestBody FollowUserReqVO followUserReqVO) {
        return relationService.follow(followUserReqVO);
    }

    /**
     * 取关用户
     *
     * @param unfollowUserReqVO 取关用户请求参数
     * @return 响应结果
     */
    @PostMapping("/unfollow")
    @ApiOperationLog(description = "取关用户")
    public Response<?> unfollow(@Validated @RequestBody UnfollowUserReqVO unfollowUserReqVO) {
        return relationService.unfollow(unfollowUserReqVO);
    }

    /**
     * 关注列表
     *
     * @return 响应结果
     */
    @PostMapping("/following/list")
    @ApiOperationLog(description = "关注列表")
    public PageResponse<FindFollowingUserRspVO> getFollows(
            @Validated @RequestBody FindFollowingListReqVO findFollowingListReqVO) {
        if (findFollowingListReqVO.getPageSize() > 10) {
            return (PageResponse) Response.fail(ResponseCodeEnum.PAGE_SIZE_LIMIT);
        }
        return relationService.findFollowingList(findFollowingListReqVO);
    }

    /**
     * 用户粉丝
     *
     * @return 响应结果
     */
    @GetMapping("/fans")
    @ApiOperationLog(description = "用户粉丝")
    public Response<?> getFans() {
        return relationService.getFans();
    }

}
