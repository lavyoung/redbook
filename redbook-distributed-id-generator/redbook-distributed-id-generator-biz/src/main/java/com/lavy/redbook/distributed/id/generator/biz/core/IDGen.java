package com.lavy.redbook.distributed.id.generator.biz.core;


import com.lavy.redbook.distributed.id.generator.biz.core.common.Result;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
public interface IDGen {
    Result get(String key);

    boolean init();
}
