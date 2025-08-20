package com.lavy.redbook.user.relation.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FansDO;
import com.lavy.redbook.user.relation.biz.domain.mapper.FansDOMapper;
import com.lavy.redbook.user.relation.biz.service.FansService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 粉丝服务实现类
 */
@Service
@Slf4j
public class FansServiceImpl extends ServiceImpl<FansDOMapper, FansDO> implements FansService {


    @Override
    public List<FansDO> getFans(Long userId) {
        if (userId == null) {
            return null;
        }
        return this.baseMapper.selectList(Wrappers.lambdaQuery(FansDO.class)
                .eq(FansDO::getUserId, userId));
    }

    @Override
    public int deleteFans(Long userId, Long fansId) {
        if (userId == null || fansId == null) {
            log.warn("invalid params: userId:{} or fansId:{} is null", userId, fansId);
            return 0;
        }
        return this.baseMapper.delete(
                Wrappers.lambdaQuery(FansDO.class).eq(FansDO::getUserId, userId).eq(FansDO::getFansUserId, fansId));
    }

    @Override
    public long selectCountByUserId(Long userId) {
        return this.baseMapper.selectCountByUserId(userId);
    }

    @Override
    public List<FansDO> selectPageListByUserId(Long userId, long offset, long limit) {
        return this.baseMapper.selectPageListByUserId(userId, offset, limit);
    }
}
