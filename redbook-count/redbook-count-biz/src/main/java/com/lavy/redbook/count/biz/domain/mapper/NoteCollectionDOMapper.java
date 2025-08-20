package com.lavy.redbook.count.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.count.biz.domain.dataobject.NoteCollectionDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/17
 * @version: v1.0.0
 * @description:
 */
public interface NoteCollectionDOMapper extends BaseMapper<NoteCollectionDO> {

    /**
     * 删除
     *
     * @param id 主键
     * @return 删除数量
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record 收藏
     * @return 新增数量
     */
    int insert(NoteCollectionDO record);

    /**
     * 新增
     *
     * @param record 收藏
     * @return 新增数量
     */
    int insertSelective(NoteCollectionDO record);

    /**
     * 查询
     *
     * @param id 主键
     * @return 收藏
     */
    NoteCollectionDO selectByPrimaryKey(Long id);

    /**
     * 更新
     *
     * @param record 收藏
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(NoteCollectionDO record);

    /**
     * 更新
     *
     * @param record 收藏
     * @return 更新数量
     */
    int updateByPrimaryKey(NoteCollectionDO record);
}