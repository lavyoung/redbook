package com.lavy.redbook.user.relation.biz.domain.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 粉丝关系表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_fans")
public class FansDO {
    @TableId
    private Long id;

    private Long userId;

    private Long fansUserId;

    private LocalDateTime createTime;
}