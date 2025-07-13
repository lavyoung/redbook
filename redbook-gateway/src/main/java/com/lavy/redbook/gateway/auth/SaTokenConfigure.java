package com.lavy.redbook.gateway.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: Sa-Token 权限认证
 */
@Configuration
public class SaTokenConfigure {

    /**
     * 注册sa-token全局过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                .addInclude("/**")
                .setAuth(auth -> {
                    SaRouter.match("/**")
                            .notMatch("/auth/user/doLogin") // 登录接口放行
                            .notMatch("/auth/verificationCode/send") // 验证码接口放行
                            .check(r -> StpUtil.checkLogin()); // 验证是否登录

                    // 用户注销登录验证权限
                    SaRouter.match("/auth/user/logout", () -> StpUtil.checkRole("common_user"));
                }).setError(e -> {
                    // return SaResult.error(e.getMessage());
                    // 手动抛出异常，抛给全局异常处理器
                    if (e instanceof NotLoginException) { // 未登录异常
                        throw new NotLoginException(e.getMessage(), null, null);
                    } else if (e instanceof NotPermissionException
                            || e instanceof NotRoleException) { // 权限不足，或不具备角色，统一抛出权限不足异常
                        throw new NotPermissionException(e.getMessage());
                    } else { // 其他异常，则抛出一个运行时异常
                        throw new RuntimeException(e.getMessage());
                    }
                });
    }
}
