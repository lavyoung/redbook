package com.lavy.redbook.auth.domain.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.auth.domain.dataobject.PermissionDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: PermissionDOMapper
 */
public interface PermissionDOMapper extends BaseMapper<PermissionDO> {

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record 实体
     * @return 新增结果
     */
    @Override
    int insert(PermissionDO record);

    /**
     * 选择性新增
     *
     * @param record 待插入的实体
     * @return 新增结果
     */
    int insertSelective(PermissionDO record);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 查询结果
     */
    PermissionDO selectByPrimaryKey(Long id);

    /**
     * 选择性更新
     *
     * @param record 待更新的实体
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(PermissionDO record);

    /**
     * 根据主键更新
     *
     * @param record 待更新的实体
     * @return 更新结果
     */
    int updateByPrimaryKey(PermissionDO record);

    /**
     * 查询 APP 端所有被启用的权限
     *
     * @return 权限列表
     */
    List<PermissionDO> selectAppEnabledList();
}