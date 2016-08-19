package com.jackfangqi.commonutil.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;

import java.util.List;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/19 16:37
 */
public final class AppUtil {
    private AppUtil() {
        throw new AssertionError();
    }

    public static String getVerionName(Context context) {
        if (context == null)
            return null;

        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(e.getMessage());
        }

        return null;
    }

    public static int getVersionCode(Context context) {
        if (context == null)
            return 0;

        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(e.getMessage());
        }

        return 0;
    }

    public static boolean isAppInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && !runningTasks.isEmpty()) {
            ComponentName topActivity = runningTasks.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName()))
                return true;
        }

        return false;
    }

    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null)
            return false;

        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        if (processInfos == null || processInfos.isEmpty())
            return false;

        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo != null && processInfo.pid == pid && processInfo.processName.equals(processName))
                return true;
        }

        return false;
    }
}
