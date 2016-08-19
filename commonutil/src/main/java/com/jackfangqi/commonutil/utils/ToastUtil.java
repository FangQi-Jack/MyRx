package com.jackfangqi.commonutil.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Compiler: Android Studio
 * Project: CommonUtils
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/18 17:25
 */
public final class ToastUtil {

    private static int DEFAULT_DURATION = Toast.LENGTH_SHORT;

    private ToastUtil() {
        throw new AssertionError();
    }

    public static void show(Context context, CharSequence msg) {
        show(context, msg, DEFAULT_DURATION);
    }

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), DEFAULT_DURATION);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), DEFAULT_DURATION);
    }

    public static void show(Context context, String msg, Object... args) {
        show(context, String.format(msg, args), DEFAULT_DURATION);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String msg, int duration, Object... args) {
        show(context, String.format(msg, args), duration);
    }

}
