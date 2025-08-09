package com.lavy.redbook.framework.common.util;

import java.util.Random;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-08-09
 */
public class RandomUtils {


    private static final Random RANDOM = new Random();


    public static long randomNumber(long bound) {
        return RANDOM.nextLong(bound);
    }
}
