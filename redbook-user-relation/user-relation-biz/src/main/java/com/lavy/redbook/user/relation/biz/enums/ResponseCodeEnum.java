package com.lavy.redbook.user.relation.biz.enums;

import com.lavy.redbook.framework.common.exception.BaseExceptionInterface;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 响应码枚举
 */
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("USER-RELATION-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("USER-RELATION-10001", "参数错误"),
    LOGIN_NOT_VALID("USER-RELATION-10002", "登录无效"),

    // ----------- 业务异常状态码 -----------
    CANT_FOLLOW_YOUR_SELF("RELATION-20001", "无法关注自己"),
    FOLLOW_USER_NOT_EXISTED("RELATION-20002", "关注的用户不存在"),
    FOLLOWING_COUNT_LIMIT("RELATION-20003", "您关注的用户已达上限，请先取关部分用户"),
    ALREADY_FOLLOWED("RELATION-20004", "您已经关注了该用户"),
    CANT_UNFOLLOW_YOUR_SELF("RELATION-20005", "无法操作自己"),
    NOT_FOLLOWED("RELATION-20006", "您未关注对方，无法取关"),
    ;

    private final String errorCode;
    private final String errorMessage;

    ResponseCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
