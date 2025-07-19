package com.lavy.redbook.user.relation.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FansDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 粉丝关系表
 */
public interface FansDOMapper extends BaseMapper<FansDO> {

    /**
     * 根据主键删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增数据
     *
     * @param record 待新增数据
     * @return 新增结果
     */
    @Override
    int insert(FansDO record);

    /**
     * 新增数据, 忽略 null 字段
     *
     * @param record 待新增数据
     * @return 新增结果
     */
    int insertSelective(FansDO record);

    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 查询结果
     */
    FansDO selectByPrimaryKey(Long id);

    /**
     * 根据主键更新数据
     *
     * @param record 待更新数据
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(FansDO record);

    /**
     * 根据主键更新数据, 不忽略 null 字段
     *
     * @param record 待更新数据
     * @return 更新结果
     */
    int updateByPrimaryKey(FansDO record);
}