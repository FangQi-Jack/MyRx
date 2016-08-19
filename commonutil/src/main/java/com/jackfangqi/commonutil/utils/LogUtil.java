package com.jackfangqi.commonutil.utils;

import android.util.Log;

import com.jackfangqi.commonutil.BuildConfig;


/**
 * Compiler: Android Studio
 * Project: CommonUtils
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/18 17:13
 */
public final class LogUtil {
    private static final String LOG_TAG = "LOG_TAG";

    private static final boolean DEBUG = BuildConfig.DEBUG;

    public LogUtil() {
        throw new AssertionError();
    }

    public static void d(String log) {
        if (DEBUG)
            Log.d(LOG_TAG, log);
    }

    public static void d(String tag, String log) {
        if (DEBUG)
            Log.d(tag, log);
    }

    public static void d(String log, Throwable tr) {
        if (DEBUG)
            Log.d(LOG_TAG, log, tr);
    }

    public static void d(String tag, String log, Throwable tr) {
        if (DEBUG)
            Log.d(tag, log, tr);
    }

    public static void v(String log) {
        if (DEBUG)
            Log.v(LOG_TAG, log);
    }

    public static void v(String tag, String log) {
        if (DEBUG)
            Log.v(tag, log);
    }

    public static void v(String log, Throwable tr) {
        if (DEBUG)
            Log.v(LOG_TAG, log, tr);
    }

    public static void v(String tag, String log, Throwable tr) {
        if (DEBUG)
            Log.v(tag, log, tr);
    }

    public static void e(String log) {
        if (DEBUG)
            Log.e(LOG_TAG, log);
    }

    public static void e(String tag, String log) {
        if (DEBUG)
            Log.e(tag, log);
    }

    public static void e(String log, Throwable tr) {
        if (DEBUG)
            Log.e(LOG_TAG, log, tr);
    }

    public static void e(String tag, String log, Throwable tr) {
        if (DEBUG)
            Log.e(tag, log, tr);
    }

    public static void i(String log) {
        if (DEBUG)
            Log.i(LOG_TAG, log);
    }

    public static void i(String tag, String log) {
        if (DEBUG)
            Log.i(tag, log);
    }

    public static void i(String log, Throwable tr) {
        if (DEBUG)
            Log.i(LOG_TAG, log, tr);
    }

    public static void i(String tag, String log, Throwable tr) {
        if (DEBUG)
            Log.i(tag, log, tr);
    }

    public static void w(String log) {
        if (DEBUG)
            Log.w(LOG_TAG, log);
    }

    public static void w(String tag, String log) {
        if (DEBUG)
            Log.w(tag, log);
    }

    public static void w(String log, Throwable tr) {
        if (DEBUG)
            Log.w(LOG_TAG, log, tr);
    }

    public static void w(String tag, String log, Throwable tr) {
        if (DEBUG)
            Log.w(tag, log, tr);
    }

    public static void wtf(String log) {
        if (DEBUG)
            Log.wtf(LOG_TAG, log);
    }

    public static void wtf(String tag, String log) {
        if (DEBUG)
            Log.wtf(tag, log);
    }

    public static void wtf(String log, Throwable tr) {
        if (DEBUG)
            Log.wtf(LOG_TAG, log, tr);
    }

    public static void wtf(String tag, String log, Throwable tr) {
        if (DEBUG)
            Log.wtf(tag, log, tr);
    }
}
