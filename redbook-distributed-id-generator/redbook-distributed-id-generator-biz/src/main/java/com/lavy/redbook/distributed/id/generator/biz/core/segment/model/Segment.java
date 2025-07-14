package com.lavy.redbook.distributed.id.generator.biz.core.segment.model;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
public class Segment {
    @Getter
    @Setter
    private AtomicLong value = new AtomicLong(0);
    @Getter
    @Setter
    private volatile long max;
    @Getter
    @Setter
    private volatile int step;
    @Getter
    private SegmentBuffer buffer;

    public Segment(SegmentBuffer buffer) {
        this.buffer = buffer;
    }


    public long getIdle() {
        return this.getMax() - getValue().get();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Segment(");
        sb.append("value:");
        sb.append(value);
        sb.append(",max:");
        sb.append(max);
        sb.append(",step:");
        sb.append(step);
        sb.append(")");
        return sb.toString();
    }
}
