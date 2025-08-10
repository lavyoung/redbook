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
}
