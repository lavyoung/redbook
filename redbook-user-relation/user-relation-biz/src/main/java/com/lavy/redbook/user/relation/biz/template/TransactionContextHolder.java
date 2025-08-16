package com.lavy.redbook.user.relation.biz.template;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/26
 * @version: v1.0.0
 * @description: 事务上下文持有者
 */
@Component
@Slf4j
public class TransactionContextHolder {


    /**
     * @param supplier 函数
     * @description: 执行事务
     */
    @Transactional
    public <T> T executeTransaction(Supplier<T> supplier) {
        T result = null;
        try {
            result = supplier.get();

            log.debug("事务执行成功");
        } catch (Exception e) {
            log.error("事务执行失败：{}", supplier, e);
            throw e;
        }
        return result;
    }

    @Transactional
    public <T> T executeTransaction(Supplier<T> supplier, String message) {
        T result = null;
        try {
            result = supplier.get();

            log.debug("事务执行成功:{}", message);
        } catch (Exception e) {
            log.error("事务执行失败：{}", message, e);
            throw e;
        }
        return result;
    }
}
