package com.lavy.redbook.user.relation.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FansDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 粉丝服务
 */
public interface FansService extends IService<FansDO> {

    /**
     * 获取粉丝列表
     *
     * @param userId 用户ID
     * @return 粉丝列表
     */
    List<FansDO> getFans(Long userId);

    /**
     * 删除粉丝
     *
     * @param userId 用户ID
     * @param fansId 粉丝ID
     * @return 删除结果
     */
    int deleteFans(Long userId, Long fansId);

    /**
     * 统计
     *
     * @param userId 用户ID
     * @return 粉丝数量
     */
    long selectCountByUserId(Long userId);

    /**
     * 分页查询
     *
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 粉丝列表
     */
    List<FansDO> selectPageListByUserId(Long userId, long offset, long limit);
}
