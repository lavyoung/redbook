package com.lavy.redbook.note.biz.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记可见性枚举
 */
@Getter
@AllArgsConstructor
public enum NoteVisibleEnum {

    /**
     * 公开
     */
    PUBLIC(0),
    /**
     * 私密
     */
    PRIVATE(1);

    @EnumValue
    private final Integer code;
}
