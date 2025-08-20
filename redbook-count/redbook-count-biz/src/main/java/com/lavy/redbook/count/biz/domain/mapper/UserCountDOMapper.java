package com.lavy.redbook.count.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.count.biz.domain.dataobject.UserCountDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/17
 * @version: v1.0.0
 * @description:
 */
public interface UserCountDOMapper extends BaseMapper<UserCountDO> {

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除的行数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     *
     * @param record 数据
     * @return 插入的行数
     */
    int insert(UserCountDO record);

    /**
     * 插入数据
     *
     * @param record 数据
     * @return 插入的行数
     */
    int insertSelective(UserCountDO record);

    /**
     * 根据id查询
     *
     * @param id id
     * @return 数据
     */
    UserCountDO selectByPrimaryKey(Long id);

    /**
     * 根据id更新数据
     *
     * @param record 数据
     * @return 更新的行数
     */
    int updateByPrimaryKeySelective(UserCountDO record);

    /**
     * 根据id更新数据
     *
     * @param record 数据
     * @return 更新的行数
     */
    int updateByPrimaryKey(UserCountDO record);
}