package com.lavy.redbook.user.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.user.biz.domain.dataobject.RoleDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 角色服务
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 查询所有可用角色
     *
     * @return 角色列表
     */
    List<RoleDO> selectEnableRoles();
}
