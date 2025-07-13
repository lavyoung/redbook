package com.lavy.redbook.gateway.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.lavy.redbook.framework.common.util.JsonUtils;
import com.lavy.redbook.gateway.constant.RedisKeyConstants;

import cn.dev33.satoken.stp.StpInterface;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 自定义权限验证
 */
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 返回此 loginId 拥有的权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        log.info("## 获取用户权限列表, loginId: {}", loginId);
        String userRolesKey = RedisKeyConstants.buildUserRolesKey((Long.valueOf(loginId.toString())));
        Object object = redisTemplate.opsForValue().get(userRolesKey);
        if (object == null) {
            return List.of();
        }
        List<String> userRoleKeys = JsonUtils.parseListObject(object, String.class);
        List<String> permissionList = new ArrayList<>();
        // 获取角色权限
        List<String> rolePermissionKeys =
                userRoleKeys.stream().map(RedisKeyConstants::buildRolePermissionsKey).toList();
        if (!rolePermissionKeys.isEmpty()) {
            // 批量获取角色权限
            List<Object> rolePermissionList = redisTemplate.opsForValue().multiGet(rolePermissionKeys);
            if (rolePermissionList != null && !rolePermissionList.isEmpty()) {
                rolePermissionList.forEach(p -> {
                    List<String> strings = JsonUtils.parseListObject(p, String.class);
                    if (strings != null && !strings.isEmpty()) {
                        permissionList.addAll(strings);
                    }
                });
            }
        }
        return permissionList;
    }

    /**
     * 返回此 loginId 拥有的角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的角色列表
        log.info("## 获取用户角色列表, loginId: {}", loginId);
        String userRolesKey = RedisKeyConstants.buildUserRolesKey((Long.valueOf(loginId.toString())));
        Object object = redisTemplate.opsForValue().get(userRolesKey);
        if (object == null) {
            return List.of();
        }
        return JsonUtils.parseListObject(object, String.class);
    }
}
