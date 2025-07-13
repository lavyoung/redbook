package com.lavy.redbook.user.biz.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.user.biz.domain.dataobject.RolePermissionRelDO;
import com.lavy.redbook.user.biz.domain.mapper.RolePermissionRelDOMapper;
import com.lavy.redbook.user.biz.service.RolePermissionRelService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户权限关系服务实现类
 */
@Service
@Slf4j
public class RolePermissionRelServiceImpl extends ServiceImpl<RolePermissionRelDOMapper, RolePermissionRelDO> implements
        RolePermissionRelService {

    /**
     * 根据角色id列表查询角色权限关系列表
     *
     * @param roleIds 角色id列表
     * @return 角色权限关系列表
     */
    @Override
    public List<RolePermissionRelDO> selectRolePermissionRelsByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return this.baseMapper.selectByRoleIds(roleIds);
    }
}
