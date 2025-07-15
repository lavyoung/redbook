package com.lavy.redbook.note.biz.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记类型枚举
 */
@Getter
@AllArgsConstructor
public enum NoteTypeEnum {

    IMAGE_TEXT(0, "图文"),
    VIDEO(1, "视频"),
    ;
    @EnumValue
    private final Integer code;
    @JsonValue
    private final String description;


    /**
     * 校验枚举值是否有效
     *
     * @param code 枚举值
     * @return 是否有效
     */
    public static boolean isValid(Integer code) {
        for (NoteTypeEnum value : NoteTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据枚举值获取枚举对象
     *
     * @param code 枚举值
     * @return 枚举对象
     */
    public static NoteTypeEnum valueOf(Integer code) {
        for (NoteTypeEnum value : NoteTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
