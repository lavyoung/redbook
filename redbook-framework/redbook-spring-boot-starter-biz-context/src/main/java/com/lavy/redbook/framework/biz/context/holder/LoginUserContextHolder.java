package com.lavy.redbook.framework.biz.context.holder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.lavy.redbook.framework.common.constant.ApiHeaderConstant;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 登录用户信息上下文
 */
public class LoginUserContextHolder {

    private static final ThreadLocal<Map<String, Object>> LOGIN_USER_CONTEXT_THREAD_LOCAL =
            TransmittableThreadLocal.withInitial(HashMap::new);

    /**
     * 设置用户 ID
     *
     * @param value 用户 ID
     */
    public static void setUserId(Object value) {
        LOGIN_USER_CONTEXT_THREAD_LOCAL.get().put(ApiHeaderConstant.HEADER_USER_ID, value);
    }

    /**
     * 获取用户 ID
     *
     * @return 用户 ID
     */
    public static Long getUserId() {
        Object value = LOGIN_USER_CONTEXT_THREAD_LOCAL.get().get(ApiHeaderConstant.HEADER_USER_ID);
        if (Objects.isNull(value)) {
            return null;
        }
        return Long.valueOf(value.toString());
    }

    /**
     * 删除 ThreadLocal
     */
    public static void remove() {
        LOGIN_USER_CONTEXT_THREAD_LOCAL.remove();
    }
}
