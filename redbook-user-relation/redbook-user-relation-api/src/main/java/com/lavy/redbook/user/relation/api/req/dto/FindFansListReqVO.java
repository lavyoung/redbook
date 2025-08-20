package com.lavy.redbook.user.relation.api.req.dto;

import com.lavy.redbook.framework.common.req.PageCommonQuery;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/17
 * @version: v1.0.0
 * @description: 查询用户关注列表请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindFansListReqVO extends PageCommonQuery {

    @NotNull(message = "查询用户 ID 不能为空")
    private Long userId;
}
