package com.lavy.redbook.framework.common.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/16
 * @version: v1.0.0
 * @description: 分页响应
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PageResponse<T> extends Response<List<T>> {

    private Long pageNo;
    private Long pageSize;
    private Long total;
    private Long totalPage;

    /**
     * 创建分页响应
     *
     * @param data 数据
     * @param pageNo 页码
     * @param pageSize 页大小
     * @param total 总数
     * @return 分页响应
     */
    public static <T> PageResponse<T> success(List<T> data, long pageNo, long pageSize, long total) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotal(total);
        response.setData(data);
        response.setTotalPage(calcTotalPage(total, pageSize));
        return response;
    }

    /**
     * 计算总页数
     *
     * @param total 总数
     * @param pageSize 页大小
     * @return 总页数
     */
    private static long calcTotalPage(long total, long pageSize) {
        if (total == 0 || pageSize == 0) {
            return 0;
        }
        return (total + pageSize - 1) / pageSize;
    }

}
