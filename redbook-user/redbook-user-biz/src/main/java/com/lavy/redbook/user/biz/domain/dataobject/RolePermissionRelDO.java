package com.lavy.redbook.user.biz.domain.dataobject;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户权限关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("t_user_permission_rel")
public class RolePermissionRelDO {
    @TableId
    private Long id;

    private Long roleId;

    private Long permissionId;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

}