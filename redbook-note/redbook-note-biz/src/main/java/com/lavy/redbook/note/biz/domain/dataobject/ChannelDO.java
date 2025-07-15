package com.lavy.redbook.note.biz.domain.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 频道
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_channel")
public class ChannelDO {
    @TableId
    private Long id;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;
}