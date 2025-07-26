package com.lavy.redbook.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.lavy.redbook.framework.common.constant.ApiHeaderConstant;
import com.lavy.redbook.gateway.constant.RedisKeyConstants;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 转发请求时，将用户 ID 添加到 Header 请求头中，透传给下游服务
 */
@Component
@Order(-99)
@Slf4j
public class AddUserId2HeaderFilter implements GlobalFilter {

    /**
     * Header 头中 Token 的 Key
     */
    private static final String TOKEN_HEADER_KEY = "Authorization";

    /**
     * Token 前缀
     */
    private static final String TOKEN_HEADER_VALUE_PREFIX = "Bearer ";


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @param exchange 表示当前的 HTTP 请求和响应的上下文，包括请求头、请求体、响应头、响应体等信息。可以通过它来获取和修改请求和响应
     * @param chain 代表网关过滤器链，通过调用 chain.filter(exchange) 方法可以将请求传递给下一个过滤器进行处理
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("==================> AddUserId2HeaderFilter <======================");
        long userId;
        try {
            userId = StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            log.debug("==> 没有登录无需处理，直接放行，可能存在取值失败");
            List<String> tokens = exchange.getRequest().getHeaders().get(TOKEN_HEADER_KEY);
            if (!CollectionUtils.isEmpty(tokens)) {
                String token = tokens.getFirst();
                if (StringUtils.hasText(token)) {
                    token = token.replace(TOKEN_HEADER_VALUE_PREFIX, "");
                    String key = RedisKeyConstants.SA_TOKEN_TOKEN_KEY_PREFIX + token;
                    Object object = redisTemplate.opsForValue().get(key);
                    if (object != null) {
                        ServerWebExchange webExchange = exchange.mutate()
                                .request(request -> request.header(ApiHeaderConstant.HEADER_USER_ID,
                                        String.valueOf(Long.parseLong(String.valueOf(object))))).build();
                        return chain.filter(webExchange);
                    }
                }
            }
            return chain.filter(exchange);
        }
        log.debug("==> 当前登录用户ID: {}", userId);
        ServerWebExchange webExchange = exchange.mutate()
                .request(request -> request.header(ApiHeaderConstant.HEADER_USER_ID, String.valueOf(userId))).build();
        return chain.filter(webExchange);
    }
}
