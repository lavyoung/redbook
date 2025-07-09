package com.lavy.redbook.framework.biz.operationlog.aspect;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.lavy.redbook.framework.common.util.JsonUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-09
 */
@Aspect
@Slf4j
public class ApiOperationLogAspect {

    /**
     * 拦截所有使用ApiOperationLog注解的方法
     */
    @Pointcut("@annotation(com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog)")
    public void apiOperationLog() {
    }

    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始时间
        long startTime = System.currentTimeMillis();
        // 类名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        // 方法名
        String methodName = joinPoint.getSignature().getName();
        // 入参
        Object[] args = joinPoint.getArgs();
        //  入参转为json字符串
        String argsStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(","));

        // 描述信息
        String description = getDescription(joinPoint);
        // 打印请求相关参数
        log.info("请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {}", description, argsStr, className, methodName);
        Object result = joinPoint.proceed();
        long executeTime = System.currentTimeMillis();
        log.info("请求结束: [{}], 耗时: {}ms, 返回值: {}", description, executeTime - startTime, JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 获取方法描述
     *
     * @param joinPoint 切点
     * @return 方法描述
     */
    private String getDescription(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ApiOperationLog apiOperationLog = signature.getMethod().getAnnotation(ApiOperationLog.class);
        return apiOperationLog.description();
    }

    /**
     * 转 JSON 字符串
     */
    private Function<Object, String> toJsonStr() {
        return JsonUtils::toJsonString;
    }
}
