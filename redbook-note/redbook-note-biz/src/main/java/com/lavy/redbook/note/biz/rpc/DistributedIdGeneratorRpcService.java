package com.lavy.redbook.note.biz.rpc;

import org.springframework.stereotype.Component;

import com.lavy.redbook.distributed.id.generator.api.client.DistributedIdGeneratorFeignClient;

import jakarta.annotation.Resource;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记服务RPC服务
 */
@Component
public class DistributedIdGeneratorRpcService {

    @Resource
    private DistributedIdGeneratorFeignClient client;


    /**
     * 获取雪花 ID
     *
     * @return 雪花 ID
     */
    public String getSnowflakeId() {
        return client.getSnowflakeId("redbook-note-t");
    }
}
