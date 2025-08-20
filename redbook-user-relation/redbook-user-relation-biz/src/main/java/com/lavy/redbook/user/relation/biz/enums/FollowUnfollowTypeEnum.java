package com.lavy.redbook.user.relation.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/20
 * @version: v1.0.0
 * @description: 关注、取关 Type
 */
@AllArgsConstructor
@Getter
public enum FollowUnfollowTypeEnum {

    FOLLOW(1),
    UNFOLLOW(2);

    private final Integer type;

    public static FollowUnfollowTypeEnum getByType(Integer type) {
        for (FollowUnfollowTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
