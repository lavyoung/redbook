package com.lavy.redbook.framework.biz.context.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.constant.ApiHeaderConstant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 添加用户ID到上下文
 */
@Slf4j
public class HeaderUserId2ContextFilter extends OncePerRequestFilter {

    /**
     * 过滤器
     *
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     * @throws ServletException 抛出异常
     * @throws IOException 抛出异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String userId = request.getHeader(ApiHeaderConstant.HEADER_USER_ID);
        if (StringUtils.isBlank(userId)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("==> 用户ID: {}, 设置到ThreadLocal中", userId);
        LoginUserContextHolder.setUserId(userId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            LoginUserContextHolder.remove();
            log.debug("==> 用户ID: {}, 移除ThreadLocal中", userId);
        }
    }
}
