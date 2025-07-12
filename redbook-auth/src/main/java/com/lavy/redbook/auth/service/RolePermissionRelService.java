package com.lavy.redbook.auth.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.auth.domain.dataobject.RolePermissionRelDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户权限关系服务
 */
public interface RolePermissionRelService extends IService<RolePermissionRelDO> {
    /**
     * 根据角色id列表查询角色权限关系列表
     *
     * @param roleIds 角色id列表
     * @return 角色权限关系列表
     */
    List<RolePermissionRelDO> selectRolePermissionRelsByRoleIds(List<Long> roleIds);
}
