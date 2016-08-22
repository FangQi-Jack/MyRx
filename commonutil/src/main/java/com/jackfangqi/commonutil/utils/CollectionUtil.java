package com.jackfangqi.commonutil.utils;

import java.util.Collection;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/19 17:16
 */
public final class CollectionUtil {
    private CollectionUtil() {
        throw new AssertionError();
    }

    public static <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.isEmpty();
    }
}
