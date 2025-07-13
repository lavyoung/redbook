package com.lavy.redbook.framework.common.util;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 参数工具类
 */
public class ParamUtils {

    // ======== 校验昵称
    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 24;

    // 定义特殊字符的正则
    private static final String NICKNAME_REGEX = "[!@#$%^&*(),.?\\\":{}|<>]";

    /**
     * 校验昵称
     *
     * @param nickname 昵称
     * @return 校验结果
     */
    public static boolean checkNickname(String nickname) {
        if (nickname == null || nickname.length() < MIN_NICKNAME_LENGTH || nickname.length() > MAX_NICKNAME_LENGTH) {
            return false;
        }
        if (nickname.matches(NICKNAME_REGEX)) {
            return false;
        }
        return true;
    }


    // ========= 校验小红书号 =========
    private static final int MIN_REDBOOK_ID_LENGTH = 6;
    private static final int MAX_REDBOOK_ID_LENGTH = 16;

    // 小红书号正则
    private static final String REDBOOK_ID_REGEX = "^[a-zA-Z0-9_]+$";

    /**
     * 校验小红书号
     *
     * @param redbookId 小红书号
     * @return 是否合法
     */
    public static boolean isValidRedbookId(String redbookId) {
        return redbookId != null && redbookId.length() >= MIN_REDBOOK_ID_LENGTH
                && redbookId.length() <= MAX_REDBOOK_ID_LENGTH && redbookId.matches(REDBOOK_ID_REGEX);
    }

    /**
     * 校验字符串长度
     *
     * @param str 字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 是否合法
     */
    public static boolean checkStringLength(String str, int minLength, int maxLength) {
        return str != null && str.length() >= minLength && str.length() <= maxLength;
    }
}
