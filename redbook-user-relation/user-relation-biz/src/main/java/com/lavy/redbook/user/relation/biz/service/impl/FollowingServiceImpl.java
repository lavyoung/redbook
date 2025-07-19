package com.lavy.redbook.user.relation.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FollowingDO;
import com.lavy.redbook.user.relation.biz.domain.mapper.FollowingDOMapper;
import com.lavy.redbook.user.relation.biz.service.FollowingService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 关注服务实现类
 */
@Service
@Slf4j
public class FollowingServiceImpl extends ServiceImpl<FollowingDOMapper, FollowingDO> implements FollowingService {


    @Override
    public List<FollowingDO> selectByUserId(Long userId) {
        return this.baseMapper.selectByUserId(userId);
    }
}
