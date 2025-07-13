package com.lavy.redbook.auth.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.auth.domain.dataobject.UserRoleRelDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 角色用户关系表
 */
public interface UserRoleRelDOMapper extends BaseMapper<UserRoleRelDO> {

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
    int insert(UserRoleRelDO record);

    /**
     * 选择性插入数据
     *
     * @param record 数据
     * @return 插入数量
     */
    int insertSelective(UserRoleRelDO record);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 数据
     */
    UserRoleRelDO selectByPrimaryKey(Long id);

    /**
     * 选择性更新数据
     *
     * @param record 数据
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(UserRoleRelDO record);

    /**
     * 根据主键更新数据
     *
     * @param record 数据
     * @return 更新数量
     */
    int updateByPrimaryKey(UserRoleRelDO record);

    /**
     * 根据用户id查询
     *
     * @param userId 用户id
     * @return 数据列表
     */
    List<UserRoleRelDO> selectListByUserId(@Param("userId") Long userId);
}