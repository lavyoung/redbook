package com.lavy.redbook.framework.biz.operationlog.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {

    /**
     * 操作描述
     *
     * @return String
     */
    String description() default "";
}
