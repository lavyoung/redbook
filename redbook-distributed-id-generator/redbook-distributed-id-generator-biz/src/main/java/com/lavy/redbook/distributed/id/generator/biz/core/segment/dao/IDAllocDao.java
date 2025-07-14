package com.lavy.redbook.distributed.id.generator.biz.core.segment.dao;

import java.util.List;

import com.lavy.redbook.distributed.id.generator.biz.core.segment.model.LeafAlloc;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
public interface IDAllocDao {
    /**
     * 获取所有LeafAlloc
     */
    List<LeafAlloc> getAllLeafAllocs();

    /**
     * 更新maxId并返回LeafAlloc
     */
    LeafAlloc updateMaxIdAndGetLeafAlloc(String tag);

    /**
     * 自定义步长更新maxId并返回LeafAlloc
     */
    LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc);

    /**
     * 获取所有tag
     */
    List<String> getAllTags();
}
