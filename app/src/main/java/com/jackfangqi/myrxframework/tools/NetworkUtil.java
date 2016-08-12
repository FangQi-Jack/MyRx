package com.jackfangqi.myrxframework.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Compiler: Android Studio
 * Project: MyRxFramework
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/12 10:14
 */
public class NetworkUtil {
    private NetworkUtil() {
        throw new UnsupportedOperationException("NetworkUtil cannot be initialized");
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        }

        return null;
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWiFiConnected(Context context) {
        return isNetworkConnected(context) && getNetworkInfo(context).getType() == ConnectivityManager.TYPE_WIFI;
    }
}
