package com.lavy.redbook.distributed.id.generator.biz.core.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
@Data
@AllArgsConstructor
public class CheckVO {
    private long timestamp;
    private int workID;
}
