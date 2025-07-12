package com.lavy.redbook.auth.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.auth.domain.dataobject.UserRoleRelDO;
import com.lavy.redbook.auth.domain.mapper.UserRoleRelDOMapper;
import com.lavy.redbook.auth.service.UserRoleRelService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户角色关系服务实现类
 */
@Service
@Slf4j
public class UserRoleRelServiceImpl extends ServiceImpl<UserRoleRelDOMapper, UserRoleRelDO> implements
        UserRoleRelService {
}
