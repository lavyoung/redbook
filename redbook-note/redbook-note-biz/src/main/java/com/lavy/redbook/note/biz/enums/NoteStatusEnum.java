package com.lavy.redbook.note.biz.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记状态枚举
 */
@Getter
@AllArgsConstructor
public enum NoteStatusEnum {
    /**
     * 待审核
     */
    BE_EXAMINE(0),
    /**
     * 正常
     */
    NORMAL(1),
    /**
     * 删除
     */
    DELETED(2),
    /**
     * 下架
     */
    DOWNED(3),
    ;
    @EnumValue
    private final Integer code;
}
