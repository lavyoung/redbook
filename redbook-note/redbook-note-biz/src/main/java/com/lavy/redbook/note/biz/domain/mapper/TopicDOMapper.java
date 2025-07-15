package com.lavy.redbook.note.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.note.biz.domain.dataobject.TopicDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: TopicDOMapper
 */
public interface TopicDOMapper extends BaseMapper<TopicDO> {

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 影响行数
     */
    @Override
    int insert(TopicDO record);

    /**
     * 新增数据, 忽略 null 字段
     *
     * @param record 插入数据
     * @return 影响行数
     */
    int insertSelective(TopicDO record);

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    TopicDO selectByPrimaryKey(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param record 实例对象
     * @return 对象列表
     */
    int updateByPrimaryKeySelective(TopicDO record);

    /**
     * 通过主键修改数据
     *
     * @param record 实例对象
     */
    int updateByPrimaryKey(TopicDO record);
}