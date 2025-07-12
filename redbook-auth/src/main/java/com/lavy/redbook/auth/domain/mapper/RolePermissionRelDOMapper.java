package com.lavy.redbook.auth.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.auth.domain.dataobject.RolePermissionRelDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户权限关系表
 */
public interface RolePermissionRelDOMapper extends BaseMapper<RolePermissionRelDO> {

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
     * @param record 数据
     * @return 插入结果
     */
    @Override
    int insert(RolePermissionRelDO record);

    /**
     * 选择性插入数据
     *
     * @param record 数据
     * @return 插入结果
     */
    int insertSelective(RolePermissionRelDO record);

    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 查询结果
     */
    RolePermissionRelDO selectByPrimaryKey(Long id);

    /**
     * 选择性更新数据
     *
     * @param record 数据
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(RolePermissionRelDO record);

    /**
     * 根据主键更新数据
     *
     * @param record 数据
     * @return 更新结果
     */
    int updateByPrimaryKey(RolePermissionRelDO record);

    /**
     * 根据角色id查询权限关系
     *
     * @param roleIds 角色id
     * @return 查询结果
     */
    List<RolePermissionRelDO> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}