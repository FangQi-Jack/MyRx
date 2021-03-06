package com.jackfangqi.commonutil.utils;

import android.os.Build;

/**
 * Compiler: Android Studio
 * Project: CommonUtils
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/18 17:44
 */
public final class AndroidVersionUtil {

    private AndroidVersionUtil() {
        throw new AssertionError();
    }

    public static int getCurrentSDKInt() {
        return Build.VERSION.SDK_INT;
    }

    public static String getCurrentOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Android 4.0 API 14
     */
    public static boolean isIceCreamSandwich() {
        return getCurrentSDKInt() == Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Android 4.0.3 API 15
     */
    public static boolean isIceCreamSandWickMR1() {
        return getCurrentSDKInt() == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * Android 4.1 API 16
     */
    public static boolean isJellyBean() {
        return getCurrentSDKInt() == Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Android 4.2 API 17
     */
    public static boolean isJellyBeanMR1() {
        return getCurrentSDKInt() == Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * Android 4.3 API 18
     */
    public static boolean isJellyBeanMR2() {
        return getCurrentSDKInt() == Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * Android 4.4 API 19
     */
    public static boolean isKitkat() {
        return getCurrentSDKInt() == Build.VERSION_CODES.KITKAT;
    }

    /**
     * Android 5.0 API 21
     */
    public static boolean isLollipop() {
        return getCurrentSDKInt() == Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Android 5.0 API 22
     * Lollipop with an extra sugar coating on the outside!
     */
    public static boolean isLollipopMR1() {
        return getCurrentSDKInt() == Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * Android 6.0 API 23
     */
    public static boolean isMarshmallow() {
        return getCurrentSDKInt() == Build.VERSION_CODES.M;
    }

    public static boolean isGingerbreadOrHigher() {
        return getCurrentSDKInt() >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Android 4.4-
     */
    public static boolean isBeforeKitkat() {
        return getCurrentSDKInt() < Build.VERSION_CODES.KITKAT;
    }

    /**
     * Android 5.0-
     */
    public static boolean isBeforeLollipop() {
        return getCurrentSDKInt() < Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Android 6.0+
     */
    public static boolean isAfterMarshmallow() {
        return getCurrentSDKInt() > Build.VERSION_CODES.M;
    }
}
