package com.jackfangqi.commonutil.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/19 9:14
 */
public final class IoUtil {
    private static final String LOG_TAG = IoUtil.class.getSimpleName();

    private IoUtil() {
        throw new AssertionError();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtil.d(LOG_TAG, e.getMessage());
                throw new RuntimeException("IOException: " + e);
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtil.d(LOG_TAG, e.getMessage());
//                throw new RuntimeException("IOException: " + e);
            }
        }
    }
}
