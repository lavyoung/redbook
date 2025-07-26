package com.lavy.redbook.gateway.constant;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: redis key常量
 */
public class RedisKeyConstants {

    /**
     * 用户角色关系缓存key前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    /**
     * 角色权限关系缓存key前缀
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     * Sa-Token 登录的 Token KEY 前缀
     */
    public static final String SA_TOKEN_TOKEN_KEY_PREFIX = "Authorization:login:token:";


    /**
     * 构建用户角色关系缓存key
     *
     * @param userId 用户id
     * @return 缓存key
     */
    public static String buildUserRolesKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

    /**
     * 构建角色权限关系缓存key
     *
     * @param roleKey 角色key
     * @return 缓存key
     */
    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }
}
