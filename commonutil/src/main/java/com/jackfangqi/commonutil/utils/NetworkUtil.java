package com.jackfangqi.commonutil.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/19 17:30
 */
public final class NetworkUtil {
    private NetworkUtil() {
        throw new AssertionError();
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();
    }

    public static int getNetworkType(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    public static boolean isWiFiConnected(Context context) {
        return getNetworkType(context) == ConnectivityManager.TYPE_WIFI;
    }
}
