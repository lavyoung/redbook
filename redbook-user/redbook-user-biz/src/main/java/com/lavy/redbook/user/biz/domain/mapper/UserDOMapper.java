package com.lavy.redbook.user.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.user.biz.domain.dataobject.UserDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: MyBatis 配置
 */
public interface UserDOMapper extends BaseMapper<UserDO> {

    /**
     * 删除数据
     *
     * @param id 用户id
     * @return 删除数量
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     *
     * @param record 用户信息
     * @return 插入数量
     */
    @Override
    int insert(UserDO record);

    /**
     * 插入数据
     *
     * @param record 用户
     * @return 插入数量
     */
    int insertSelective(UserDO record);

    /**
     * 根据id 查询用户
     *
     * @param id id
     * @return UserDO
     */
    UserDO selectByPrimaryKey(Long id);

    /**
     * 更新 数据
     *
     * @param record 用户
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(UserDO record);

    /**
     * 根据id更新用户信息
     *
     * @param record 用户信息
     * @return 更新数量
     */
    int updateByPrimaryKey(UserDO record);
}