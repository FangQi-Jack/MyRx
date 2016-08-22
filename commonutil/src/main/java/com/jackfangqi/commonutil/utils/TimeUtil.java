package com.jackfangqi.commonutil.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/22 9:08
 */
public final class TimeUtil {
    private static final String LOG_TAG = TimeUtil.class.getSimpleName();

    private TimeUtil() {
        throw new AssertionError();
    }

    public static SimpleDateFormat getDefaultDateFormat() {
        return createDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat createDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public static SimpleDateFormat createDateFormat(String format, Locale locale) {
        return new SimpleDateFormat(format, locale);
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String getCurrentTimeFormat() {
        return getDefaultDateFormat().format(new Date(getCurrentTimeMillis()));
    }

    public static String getCurrentTimeFormat(String format) {
        return createDateFormat(format).format(new Date(getCurrentTimeMillis()));
    }

    public static String getCurrentTimeFormat(String format, Locale locale) {
        return createDateFormat(format, locale).format(new Date(getCurrentTimeMillis()));
    }

    public static String getTimeFormat(long timeMillis) {
        return getDefaultDateFormat().format(new Date(timeMillis));
    }

    public static String getTimeFormat(String format, long timeMillis) {
        return createDateFormat(format).format(new Date(timeMillis));
    }

    public static String getTimeFormat(String format, Locale locale, long timeMillis) {
        return createDateFormat(format, locale).format(new Date(timeMillis));
    }

    public static String getDateFormat(Date date) {
        return getDefaultDateFormat().format(date);
    }

    public static String getDateFormat(String format, Date date) {
        return createDateFormat(format).format(date);
    }

    public static String getDateFormat(String format, Locale locale, Date date) {
        return createDateFormat(format, locale).format(date);
    }

}
