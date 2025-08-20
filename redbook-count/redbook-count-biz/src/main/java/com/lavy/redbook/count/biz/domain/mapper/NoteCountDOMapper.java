package com.lavy.redbook.count.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.count.biz.domain.dataobject.NoteCountDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/17
 * @version: v1.0.0
 * @description:
 */
public interface NoteCountDOMapper extends BaseMapper<NoteCountDO> {

    /**
     * 删除
     *
     * @param id id
     * @return 影响行数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record 记录
     * @return 影响行数
     */
    int insert(NoteCountDO record);

    /**
     * 新增
     *
     * @param record 记录
     * @return 影响行数
     */
    int insertSelective(NoteCountDO record);

    /**
     * 查询
     *
     * @param id id
     * @return 影响行数
     */
    NoteCountDO selectByPrimaryKey(Long id);

    /**
     * 更新
     *
     * @param record 记录
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(NoteCountDO record);

    /**
     * 更新
     *
     * @param record 记录
     * @return 影响行数
     */
    int updateByPrimaryKey(NoteCountDO record);
}