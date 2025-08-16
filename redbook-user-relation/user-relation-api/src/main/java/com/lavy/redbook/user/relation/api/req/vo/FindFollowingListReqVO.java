package com.lavy.redbook.user.relation.api.req.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/16
 * @version: v1.0.0
 * @description: 查询用户关注列表请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindFollowingListReqVO {

    @NotNull(message = "查询用户 ID 不能为空")
    private Long userId;

    @NotNull(message = "页码不能为空")
    private Integer pageNo = 1; // 默认值为第一页

    @NotNull(message = "每页数量不能为空")
    @Max(value = 100, message = "每页数量不能超过100")
    @Min(value = 1, message = "每页数量不能小于1")
    private Integer pageSize = 10;
}