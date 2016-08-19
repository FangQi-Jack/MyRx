package com.jackfangqi.commonutil.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Compiler: Android Studio
 * Project: CommonUtils
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/18 17:46
 */
public final class ScreenUtil {

    private ScreenUtil() {
        throw new AssertionError();
    }

    public static float dp2Px(Context context, float dp) {
        if (context == null)
            return -1;

        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float px2Dp(Context context, float px) {
        if (context == null)
            return -1;

        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int dp2PxInt(Context context, float dp) {
        return (int) (dp2Px(context, dp) + 0.5f);
    }

    public static int px2DpCeilDp(Context context, float px) {
        return (int) (px2Dp(context, px) + 0.5f);
    }

    public static int getScreenWidthPixels(Context context) {
        if (context == null)
            return -1;

        if (!(context instanceof Activity))
            return -1;

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeightPixels(Context context) {
        if (context == null)
            return -1;

        if (!(context instanceof Activity))
            return -1;

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;

        if (context == null)
            return statusBarHeight;

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);

        return statusBarHeight;
    }

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;

        if (context == null)
            return actionBarHeight;

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());

        return actionBarHeight;
    }
}
