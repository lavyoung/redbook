package com.lavy.redbook.distributed.id.generator.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description: 分布式id生成器 feign 客户端
 */
@FeignClient(name = "redbook-distributed-id-generator-biz", path = "")
public interface DistributedIdGeneratorFeignClient {

    String PREFIX = "/id";

    /**
     * 获取segment id
     *
     * @param key 业务标识
     * @return segment id
     */
    @RequestMapping(value = PREFIX + "/segment/get/{key}")
    String getSegmentId(@PathVariable("key") String key);

    /**
     * 获取雪花 id
     *
     * @param key 业务标识[无作用]
     * @return 雪花 id
     */
    @RequestMapping(value = PREFIX + "/snowflake/get/{key}")
    String getSnowflakeId(@PathVariable("key") String key);
}
