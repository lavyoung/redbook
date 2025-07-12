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
public enum DeletedEnum {

    YES(true),
    NO(false),
    ;
    private final Boolean value;

}
