package com.lavy.redbook.auth.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.auth.domain.dataobject.PermissionDO;
import com.lavy.redbook.auth.domain.mapper.PermissionDOMapper;
import com.lavy.redbook.auth.service.PermissionService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 权限服务实现类
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionDOMapper, PermissionDO> implements PermissionService {

    /**
     * 查询启用的权限列表
     *
     * @return 启用的权限列表
     */
    @Override
    public List<PermissionDO> selectEnablePermission() {
        return this.baseMapper.selectAppEnabledList();
    }
}
