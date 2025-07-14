package com.lavy.redbook.distributed.id.generator.biz.core.snowflake.exception;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
public class CheckLastTimeException extends RuntimeException {
    public CheckLastTimeException(String msg) {
        super(msg);
    }
}
