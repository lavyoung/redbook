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
 * @description: 角色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("t_role")
public class RoleDO {
    @TableId
    private Long id;

    private String roleName;

    private String roleKey;

    private Byte status;

    private Integer sort;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;
}