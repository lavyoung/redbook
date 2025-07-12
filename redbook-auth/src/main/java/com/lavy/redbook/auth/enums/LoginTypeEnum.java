package com.lavy.redbook.auth.enums;

import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 登录类型枚举
 */
@Getter
public enum LoginTypeEnum {

    PHONE(1),
    PASSWORD(2),
    ;
    private final Integer value;

    LoginTypeEnum(Integer value) {
        this.value = value;
    }

    public static LoginTypeEnum getByValue(Integer value) {
        for (LoginTypeEnum item : values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }
}
