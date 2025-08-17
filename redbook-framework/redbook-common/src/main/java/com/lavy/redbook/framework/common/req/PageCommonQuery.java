package com.lavy.redbook.framework.common.req;

import lombok.Data;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/17
 * @version: v1.0.0
 * @description: 分页查询通用参数
 */
@Data
public class PageCommonQuery {
    /**
     * 页码
     */
    private Long pageNo = 1L;
    /**
     * 每页数量
     */
    private Long pageSize = 10L;
    /**
     * 排序字段
     */
    private String orderBy;
    /**
     * 是否升序
     */
    private boolean isAsc = true;
    /**
     * 偏移量
     */
    private Long offset;

    /**
     * 计算偏移量
     */
    public void calculateOffset() {
        if (pageNo != null && pageSize != null) {
            this.offset = (pageNo - 1) * pageSize;
        }
    }
}
