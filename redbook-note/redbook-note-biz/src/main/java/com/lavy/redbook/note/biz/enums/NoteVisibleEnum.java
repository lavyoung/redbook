package com.lavy.redbook.note.biz.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

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
    @JsonValue
    private final Integer code;

    public static NoteVisibleEnum getByCode(Integer code) {
        for (NoteVisibleEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
