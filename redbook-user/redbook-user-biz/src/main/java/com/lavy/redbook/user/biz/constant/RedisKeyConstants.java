package com.lavy.redbook.user.biz.constant;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-10
 */
public class RedisKeyConstants {

    /**
     * 用户全局 ID 生成器 KEY
     */
    public static final String REDBOOK_ID_GENERATOR_KEY = "redbook.id.generator";

    /**
     * 用户角色数据 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    /**
     * 角色对应的权限集合 KEY 前缀
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     * 用户信息数据 KEY 前缀
     */
    private static final String USER_INFO_KEY_PREFIX = "user:info:";

    /**
     * 粉丝列表 KEY 前缀
     */
    private static final String USER_FANS_KEY_PREFIX = "fans:";

    /**
     * 用户对应的角色集合 KEY
     *
     * @param userId 用户ID
     * @return 用户对应的角色集合 KEY
     */
    public static String buildUserRoleKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

    /**
     * 构建角色-权限 Key
     */
    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }

    /**
     * 构建角色对应的权限集合 KEY
     *
     * @param userId 用户ID
     * @return 角色对应的权限集合 KEY
     */
    public static String buildUserInfoKey(Long userId) {
        return USER_INFO_KEY_PREFIX + userId;
    }

    /**
     * 构建粉丝列表完整的 KEY
     *
     * @param userId 用户ID
     * @return 粉丝列表完整的 KEY
     */
    public static String buildUserFansKey(Long userId) {
        return USER_FANS_KEY_PREFIX + userId;
    }
}
