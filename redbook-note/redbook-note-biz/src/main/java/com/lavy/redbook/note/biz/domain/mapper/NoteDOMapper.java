package com.lavy.redbook.note.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.note.biz.domain.dataobject.NoteDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记DOMapper
 */
public interface NoteDOMapper extends BaseMapper<NoteDO> {
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
     * @param record 笔记DO
     * @return 新增结果
     */
    @Override
    int insert(NoteDO record);

    /**
     * 新增数据, 忽略null字段
     *
     * @param record 笔记DO
     * @return 新增结果
     */
    int insertSelective(NoteDO record);

    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 查询结果
     */
    NoteDO selectByPrimaryKey(Long id);

    /**
     * 根据主键更新数据
     *
     * @param record 笔记DO
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(NoteDO record);

    /**
     * 根据主键更新数据, 不忽略null字段
     *
     * @param record 笔记DO
     * @return 更新结果
     */
    int updateByPrimaryKey(NoteDO record);

    /**
     * 仅对可见的笔记进行更新
     *
     * @param noteDO 笔记DO
     * @return 更新结果
     */
    int updateVisibleOnlyMe(NoteDO noteDO);

    /**
     * 仅对置顶的笔记进行更新
     *
     * @param noteDO 笔记DO
     * @return 置顶结果
     */
    int updateIsTop(NoteDO noteDO);
}