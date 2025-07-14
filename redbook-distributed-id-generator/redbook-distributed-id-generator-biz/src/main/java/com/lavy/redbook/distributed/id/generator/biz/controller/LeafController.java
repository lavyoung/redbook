package com.lavy.redbook.distributed.id.generator.biz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavy.redbook.distributed.id.generator.biz.core.common.Result;
import com.lavy.redbook.distributed.id.generator.biz.core.common.Status;
import com.lavy.redbook.distributed.id.generator.biz.exception.LeafServerException;
import com.lavy.redbook.distributed.id.generator.biz.exception.NoKeyException;
import com.lavy.redbook.distributed.id.generator.biz.service.SegmentService;
import com.lavy.redbook.distributed.id.generator.biz.service.SnowflakeService;
import com.lavy.redbook.framework.biz.operationlog.aspect.ApiOperationLog;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
@RestController
@RequestMapping(value = "/id")
public class LeafController {
    private Logger logger = LoggerFactory.getLogger(LeafController.class);

    @Autowired
    private SegmentService segmentService;
    @Autowired
    private SnowflakeService snowflakeService;

    @RequestMapping(value = "/segment/get/{key}")
    @ApiOperationLog(description = "获取segment id")
    public String getSegmentId(@PathVariable("key") String key) {
        return get(key, segmentService.getId(key));
    }

    @RequestMapping(value = "/snowflake/get/{key}")
    @ApiOperationLog(description = "获取snowflake id")
    public String getSnowflakeId(@PathVariable("key") String key) {
        return get(key, snowflakeService.getId(key));
    }

    private String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }

}
