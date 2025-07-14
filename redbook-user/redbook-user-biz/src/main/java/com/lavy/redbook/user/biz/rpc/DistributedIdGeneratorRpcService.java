package com.lavy.redbook.user.biz.rpc;

import org.springframework.stereotype.Component;

import com.lavy.redbook.distributed.id.generator.api.client.DistributedIdGeneratorFeignClient;

import jakarta.annotation.Resource;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description: 分布式ID生成服务
 */
@Component
public class DistributedIdGeneratorRpcService {

    /**
     * 默认业务标签
     */
    private static final String BIZ_TAG_RB_ID = "leaf-segment-redbook-id";
    /**
     * Leaf 号段模式：用户 ID 业务标识
     */
    private static final String BIZ_TAG_USER_ID = "leaf-segment-user-id";

    @Resource
    private DistributedIdGeneratorFeignClient client;

    /**
     * 获取 RedBook 业务 ID
     *
     * @return RedBook 业务 ID
     */
    public String getRBSegmentId() {
        return client.getSegmentId(BIZ_TAG_RB_ID);
    }

    /**
     * 获取用户 ID 业务 ID
     *
     * @return 用户 ID 业务 ID
     */
    public String getUserSegmentId() {
        return client.getSegmentId(BIZ_TAG_USER_ID);
    }

    /**
     * 获取snowflake id
     *
     * @return snowflake id
     */
    public String getSnowflakeId() {
        return client.getSnowflakeId("redbook-id-g");
    }
}
