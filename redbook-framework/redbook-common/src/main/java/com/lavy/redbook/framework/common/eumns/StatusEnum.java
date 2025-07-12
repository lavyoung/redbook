package com.lavy.redbook.framework.common.eumns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 状态枚举
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    /**
     * 启用
     */
    ENABLED(1),
    /**
     * 禁用
     */
    DISABLED(0),
    ;
    private final Integer value;
}
