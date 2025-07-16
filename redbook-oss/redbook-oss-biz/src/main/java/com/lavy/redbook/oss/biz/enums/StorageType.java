package com.lavy.redbook.oss.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 存储类型枚举
 */
@AllArgsConstructor
@Getter
public enum StorageType {

    MINIO("minio"),
    ALIYUN("aliyun"),
    TENCEN("tencent");

    private final String value;

}
