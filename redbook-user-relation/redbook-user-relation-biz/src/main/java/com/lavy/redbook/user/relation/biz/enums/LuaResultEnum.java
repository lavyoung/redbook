package com.lavy.redbook.user.relation.biz.enums;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: Lua 脚本返回结果枚举
 */
@Getter
@AllArgsConstructor
public enum LuaResultEnum {
    /**
     * 关注用户存在
     */
    ZSET_NOT_EXIST(-1L),
    /**
     * 关注用户已满
     */
    FOLLOW_LIMIT(-2L),
    /**
     * 已关注
     */
    ALREADY_FOLLOWED(-3L),
    /**
     * 关注成功
     */
    FOLLOW_SUCCESS(0L),
    /**
     * 未关注该用户
     */
    NOT_FOLLOWED(-4L),
    ;

    private final Long code;

    /**
     * 根据类型 code 获取对应的枚举
     */
    public static LuaResultEnum valueOf(Long code) {
        for (LuaResultEnum luaResultEnum : LuaResultEnum.values()) {
            if (Objects.equals(code, luaResultEnum.getCode())) {
                return luaResultEnum;
            }
        }
        return null;
    }
}
