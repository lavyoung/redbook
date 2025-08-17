package com.lavy.redbook.user.relation.biz.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询记录总数
     *
     * @param userId 用户ID
     * @return 记录总数
     */
    long selectCountByUserId(Long userId);


    /**
     * 分页查询
     *
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 查询数量
     */
    List<FansDO> selectPageListByUserId(@Param("userId") Long userId,
            @Param("offset") long offset,
            @Param("limit") long limit);
}