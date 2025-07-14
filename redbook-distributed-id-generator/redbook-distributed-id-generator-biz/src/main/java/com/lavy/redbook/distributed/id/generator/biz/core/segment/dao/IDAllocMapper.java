package com.lavy.redbook.distributed.id.generator.biz.core.segment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lavy.redbook.distributed.id.generator.biz.core.segment.model.LeafAlloc;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
public interface IDAllocMapper {

    /**
     * 获取所有业务标识
     *
     * @return 业务标识
     */
    @Select("SELECT biz_tag, max_id, step, update_time FROM leaf_alloc")
    @Results(value = {
            @Result(column = "biz_tag", property = "key"),
            @Result(column = "max_id", property = "maxId"),
            @Result(column = "step", property = "step"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<LeafAlloc> getAllLeafAllocs();

    /**
     * 获取业务标识
     *
     * @param tag 业务标识
     * @return 业务标识
     */
    @Select("SELECT biz_tag, max_id, step FROM leaf_alloc WHERE biz_tag = #{tag}")
    @Results(value = {
            @Result(column = "biz_tag", property = "key"),
            @Result(column = "max_id", property = "maxId"),
            @Result(column = "step", property = "step")
    })
    LeafAlloc getLeafAlloc(@Param("tag") String tag);

    /**
     * 更新业务标识
     *
     * @param tag 业务标识
     * @return 业务标识
     */
    @Update("UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}")
    void updateMaxId(@Param("tag") String tag);

    /**
     * 自定义步长更新业务标识
     *
     * @param leafAlloc 业务标识
     * @return 业务标识
     */
    @Update("UPDATE leaf_alloc SET max_id = max_id + #{step} WHERE biz_tag = #{key}")
    void updateMaxIdByCustomStep(@Param("leafAlloc") LeafAlloc leafAlloc);

    /**
     * 获取所有业务标识
     *
     * @return 业务标识
     */
    @Select("SELECT biz_tag FROM leaf_alloc")
    List<String> getAllTags();
}
