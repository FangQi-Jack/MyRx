package com.jackfangqi.commonutil.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/19 17:21
 */
public final class PermissionUtil {
    private PermissionUtil() {
        throw new AssertionError();
    }

    /**
     * 检查权限
     *
     * @param context    上下文
     * @param permission 权限
     * @return 是否获取了权限。true 已获取，false 未获取
     */
    public static boolean checkPermission(Context context, String permission) {
        return context != null
                && ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检查一组权限
     *
     * @param context     上下文
     * @param permissions 权限
     * @return 未授权的权限
     */
    public static List<String> checkPermissions(Context context, List<String> permissions) {
        if (context == null)
            return permissions;

        List<String> permissionDenied = new ArrayList<>();
        for (String permission : permissions) {
            if (!checkPermission(context, permission))
                permissionDenied.add(permission);
        }

        return permissionDenied;
    }
}
