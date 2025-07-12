package com.lavy.redbook.auth.domain.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.auth.domain.dataobject.RoleDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 角色DOMapper
 */
public interface RoleDOMapper extends BaseMapper<RoleDO> {

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return 删除数量
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     *
     * @param record 数据
     * @return 插入数量
     */
    @Override
    int insert(RoleDO record);

    /**
     * 插入数据（字段为空）
     *
     * @param record 数据
     * @return 插入数量
     */
    int insertSelective(RoleDO record);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 数据
     */
    RoleDO selectByPrimaryKey(Long id);

    /**
     * 根据主键更新数据
     *
     * @param record 数据
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(RoleDO record);

    /**
     * 根据主键更新数据（字段为空）
     *
     * @param record 数据
     * @return 插入数量
     */
    int updateByPrimaryKey(RoleDO record);

    /**
     * 查询所有启用的角色
     *
     * @return 角色列表
     */
    List<RoleDO> selectEnableRoles();
}