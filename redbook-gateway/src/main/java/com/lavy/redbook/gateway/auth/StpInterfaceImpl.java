package com.lavy.redbook.gateway.auth;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpInterface;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 自定义权限验证
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回此 loginId 拥有的权限列表
     */
    @Override
    public List<String> getPermissionList(Object o, String s) {
        // 返回此 loginId 拥有的权限列表

        // todo 从 redis 获取
        return List.of();
    }

    /**
     * 返回此 loginId 拥有的角色列表
     */
    @Override
    public List<String> getRoleList(Object o, String s) {
        // 返回此 loginId 拥有的角色列表

        // todo 从 redis 获取
        return List.of();
    }
}
