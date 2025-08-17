package com.lavy.redbook.count.biz.enums;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 性别枚举
 */
@Getter
@AllArgsConstructor
public enum SexEnum {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");
    private final Integer code;
    private final String message;

    /**
     * 校验性别参数
     *
     * @param value 性别参数
     * @return 校验结果
     */
    public static boolean isValid(Integer value) {
        for (SexEnum loginTypeEnum : SexEnum.values()) {
            if (Objects.equals(value, loginTypeEnum.getCode())) {
                return true;
            }
        }
        return false;
    }
}
