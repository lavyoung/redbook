package com.lavy.redbook.framework.common.util;

import java.util.Random;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/8/10
 * @version: v1.0.0
 * @description: 随机数工具类
 */
public class RandomUtils {
    /**
     * 随机数生成器
     */
    private static final Random RANDOM = new Random();


    /**
     * 获取随机数
     *
     * @param bound 随机数范围
     * @return 随机数
     */
    public static long randomNumber(long bound) {
        return RANDOM.nextLong(bound);
    }


    /**
     * 获取随机数
     *
     * @param bits 随机数位数
     * @return 随机数
     */
    public static String numberGenerator(int bits) {
        if (bits < 1 || bits > 32) {
            throw new IllegalArgumentException("Bits must be between 1 and 32");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}
