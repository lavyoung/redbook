package com.lavy.redbook.user.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.user.biz.domain.dataobject.RoleDO;
import com.lavy.redbook.user.biz.domain.mapper.RoleDOMapper;
import com.lavy.redbook.user.biz.service.RoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 角色服务实现类
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleDOMapper, RoleDO> implements RoleService {

    /**
     * 获取所有可用角色
     *
     * @return List<RoleDO>
     */
    @Override
    public List<RoleDO> selectEnableRoles() {
        return this.baseMapper.selectEnableRoles();
    }
}
