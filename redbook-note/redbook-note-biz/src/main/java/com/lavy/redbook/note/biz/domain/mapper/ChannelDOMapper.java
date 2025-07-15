package com.lavy.redbook.note.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.note.biz.domain.dataobject.ChannelDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: ChannelDOMapper
 */
public interface ChannelDOMapper extends BaseMapper<ChannelDO> {

    /**
     * 删除
     *
     * @param id id
     * @return 删除数量
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record 记录
     * @return 新增数量
     */
    @Override
    int insert(ChannelDO record);

    /**
     * 新增
     *
     * @param record 记录
     * @return 新增数量
     */
    int insertSelective(ChannelDO record);

    /**
     * 查询
     *
     * @param id id
     * @return 记录
     */
    ChannelDO selectByPrimaryKey(Long id);

    /**
     * 更新
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(ChannelDO record);

    /**
     * 更新
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKey(ChannelDO record);
}