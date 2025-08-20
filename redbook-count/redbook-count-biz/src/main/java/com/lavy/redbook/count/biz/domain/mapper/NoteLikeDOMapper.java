package com.lavy.redbook.count.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.count.biz.domain.dataobject.NoteLikeDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/17
 * @version: v1.0.0
 * @description:
 */
public interface NoteLikeDOMapper extends BaseMapper<NoteLikeDO> {

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
    int insert(NoteLikeDO record);

    /**
     * 新增
     *
     * @param record 记录
     * @return 新增数量
     */
    int insertSelective(NoteLikeDO record);

    /**
     * 查询
     *
     * @param id id
     * @return 记录
     */
    NoteLikeDO selectByPrimaryKey(Long id);

    /**
     * 更新
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(NoteLikeDO record);

    /**
     * 更新
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKey(NoteLikeDO record);
}