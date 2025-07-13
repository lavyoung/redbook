package com.lavy.redbook.user.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.user.biz.domain.dataobject.PermissionDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 权限服务
 */
public interface PermissionService extends IService<PermissionDO> {

    /**
     * 查询所有可用权限
     *
     * @return 所有可用权限
     */
    List<PermissionDO> selectEnablePermission();
}
