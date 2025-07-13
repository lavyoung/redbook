package com.lavy.redbook.user.biz.domain.dataobject;

import java.time.LocalDateTime;

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
 * @description: 用户角色关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("t_user_role_rel")
public class UserRoleRelDO {
    @TableId
    private Long id;

    private Long userId;

    private Long roleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

}