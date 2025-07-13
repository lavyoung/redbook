package com.lavy.redbook.auth.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.lavy.redbook.auth.constant.RedisKeyConstants;
import com.lavy.redbook.auth.domain.dataobject.PermissionDO;
import com.lavy.redbook.auth.domain.dataobject.RoleDO;
import com.lavy.redbook.auth.domain.dataobject.RolePermissionRelDO;
import com.lavy.redbook.auth.service.PermissionService;
import com.lavy.redbook.auth.service.RolePermissionRelService;
import com.lavy.redbook.auth.service.RoleService;
import com.lavy.redbook.framework.common.util.JsonUtils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 角色权限数据同步到 Redis 中
 */
@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

    /**
     * 角色权限数据同步到 Redis 中
     */
    private static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionRelService rolePermissionRelService;

    /**
     * 角色权限数据同步到 Redis 中
     *
     * @param args 参数
     */
    @Override
    public void run(ApplicationArguments args) {
        try {
            boolean absent = Boolean.TRUE.equals(
                    redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS));
            if (!absent) {
                log.info("==> 角色权限数据已存在，无需再次同步...");
                return;
            }
            log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");
            List<RoleDO> roleDOList = roleService.selectEnableRoles();
            if (!CollectionUtils.isEmpty(roleDOList)) {
                List<Long> roleIds = roleDOList.stream().map(RoleDO::getId).toList();
                List<RolePermissionRelDO> rolePermissions =
                        rolePermissionRelService.selectRolePermissionRelsByRoleIds(roleIds);

                // 获取角色权限关系
                Map<Long, List<Long>> rolePermissionMap = rolePermissions.stream().collect(
                        Collectors.groupingBy(RolePermissionRelDO::getRoleId,
                                Collectors.mapping(RolePermissionRelDO::getPermissionId, Collectors.toList())));
                // 获取启用的权限信息
                List<PermissionDO> permissionDOS = permissionService.selectEnablePermission();
                // 构建权限信息Map
                Map<Long, PermissionDO> permissionDOMap = permissionDOS.stream()
                        .collect(Collectors.toMap(PermissionDO::getId, permissionDO -> permissionDO));

                // 构建角色权限信息Map
                HashMap<String, List<String>> roleIdPermissionDOMap = Maps.newHashMap();

                // 循环角色
                roleDOList.forEach(roleDO -> {
                    // 获取角色权限
                    List<Long> permissionIds = rolePermissionMap.get(roleDO.getId());
                    if (!CollectionUtils.isEmpty(permissionIds)) {
                        List<String> perDOS = new ArrayList<>();
                        permissionIds.forEach(permissionId -> {
                            PermissionDO permissionDO = permissionDOMap.get(permissionId);
                            if (permissionDO != null) {
                                perDOS.add(permissionDO.getPermissionKey());
                            }
                        });
                        roleIdPermissionDOMap.put(roleDO.getRoleKey(), perDOS);
                    }
                });

                // 缓存角色权限
                roleIdPermissionDOMap.forEach((roleId, permissionDOs) -> {
                    String rolePermissionKey = RedisKeyConstants.buildRolePermissionsKey(roleId);
                    redisTemplate.opsForValue().set(rolePermissionKey, JsonUtils.toJsonString(permissionDOs));
                });
            }

        } catch (Exception e) {
            log.error("==> 服务启动，同步角色权限数据到 Redis 中失败！", e);
        }
        log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
    }
}
