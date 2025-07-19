package com.lavy.redbook.framework.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 时间工具类
 */
public class DateUtils {

    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime LocalDateTime
     * @return 时间戳
     */
    public static long localDateTime2Timestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new IllegalArgumentException("localDateTime can't be null");
        }

        // 使用固定时区 UTC 避免系统默认时区影响结果
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
