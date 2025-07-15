package com.lavy.redbook.note.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.note.biz.domain.dataobject.ChannelTopicDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 频道-话题关系DOMapper
 */
public interface ChannelTopicDOMapper extends BaseMapper<ChannelTopicDO> {

    /**
     * 根据主键删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     *
     * @param record 插入数据
     * @return 插入结果
     */
    @Override
    int insert(ChannelTopicDO record);

    /**
     * 插入数据（字段为空时，该字段不会被插入）
     *
     * @param record 插入数据
     * @return 插入结果
     */
    int insertSelective(ChannelTopicDO record);

    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 查询结果
     */
    ChannelTopicDO selectByPrimaryKey(Long id);

    /**
     * 根据主键更新数据（字段为空时，该字段不会被更新）
     *
     * @param record 更新数据
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(ChannelTopicDO record);

    /**
     * 根据主键更新数据
     *
     * @param record 更新数据
     * @return 更新结果
     */
    int updateByPrimaryKey(ChannelTopicDO record);
}