package com.jackfangqi.commonutil.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/19 9:14
 */
public final class IOUtil {
    private IOUtil() {
        throw new AssertionError();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException("IOException: " + e);
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
//                throw new RuntimeException("IOException: " + e);
            }
        }
    }
}
