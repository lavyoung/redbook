package com.lavy.redbook.auth.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.auth.domain.dataobject.UserPermissionRelDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户权限关系表
 */
public interface UserPermissionRelDOMapper extends BaseMapper<UserPermissionRelDO> {

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
    int insert(UserPermissionRelDO record);

    /**
     * 选择性插入数据
     *
     * @param record 数据
     * @return 插入结果
     */
    int insertSelective(UserPermissionRelDO record);

    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 查询结果
     */
    UserPermissionRelDO selectByPrimaryKey(Long id);

    /**
     * 选择性更新数据
     *
     * @param record 数据
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(UserPermissionRelDO record);

    /**
     * 根据主键更新数据
     *
     * @param record 数据
     * @return 更新结果
     */
    int updateByPrimaryKey(UserPermissionRelDO record);
}