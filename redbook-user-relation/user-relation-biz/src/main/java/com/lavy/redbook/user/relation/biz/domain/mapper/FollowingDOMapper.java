package com.lavy.redbook.user.relation.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.user.relation.biz.domain.dataobject.FollowingDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 粉丝关系表
 */
public interface FollowingDOMapper extends BaseMapper<FollowingDO> {

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
    @Override
    int insert(FollowingDO record);

    /**
     * 新增
     *
     * @param record 记录
     * @return 新增数量
     */
    int insertSelective(FollowingDO record);

    /**
     * 查询
     *
     * @param id id
     * @return 关注 信息
     */
    FollowingDO selectByPrimaryKey(Long id);

    /**
     * 更新
     *
     * @param record 根据id更新记录
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(FollowingDO record);

    /**
     * 更新
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKey(FollowingDO record);
}