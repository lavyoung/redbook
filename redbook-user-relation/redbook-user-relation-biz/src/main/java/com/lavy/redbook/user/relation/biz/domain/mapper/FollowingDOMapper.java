package com.lavy.redbook.user.relation.biz.domain.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lavy.redbook.user.relation.api.req.dto.FollowingPageReq;
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

    /**
     * 根据用户id查询
     *
     * @param userId 用户id
     * @return 关注列表
     */
    List<FollowingDO> selectByUserId(Long userId);

    /**
     * 分页查询
     *
     * @param pageReq 分页参数
     * @return 分页结果
     */
    List<FollowingDO> pageDO(FollowingPageReq pageReq);

    /**
     * 分页查询数量
     *
     * @param pageReq 分页参数
     * @return 分页结果数量
     */
    long pageCount(FollowingPageReq pageReq);
}