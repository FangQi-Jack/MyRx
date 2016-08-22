package com.jackfangqi.commonutil.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.jackfangqi.commonutil.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
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

    /**
     * 显示权限授予提醒框
     *
     * @param context 上下文
     * @param msg     提示文案
     */
    public static void showRequestDialog(final Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setNegativeButton(R.string.permission_request_dialog_cancel, null);
        builder.setPositiveButton(R.string.permission_request_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (context instanceof Activity)
                    context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + context.getPackageName())));
                else
                    throw new UnsupportedOperationException();

                dialog.dismiss();
            }
        });
        builder.show();
    }
}
