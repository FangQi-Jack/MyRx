package com.jackfangqi.commonutil.utils;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/22 8:48
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String LOG_TAG = CrashHandler.class.getSimpleName();

    private static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "crash_log" + File.separator;
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            // 将异常信息导入SD卡中
            dumpException2SDCard(throwable);
            // 上传异常信息到服务器
            uploadException2Server(throwable);
        } catch (IOException e) {
            LogUtil.d(LOG_TAG, "IOException-> ", e);
        }

        LogUtil.d(LOG_TAG, "uncaughtException-> ", throwable);
        // 如果提供了默认的异常处理器，就交由系统结束进程，否则自己结束进程
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, throwable);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 将异常信息导入SD卡中
     *
     * @param throwable 异常
     * @throws IOException
     */
    private void dumpException2SDCard(Throwable throwable) throws IOException {
        boolean permissionCheck = PermissionUtil.checkPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                LogUtil.d(LOG_TAG, "SD card unmounted!");
            } else {
                File dir = new File(FILE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String time = TimeUtil.getCurrentTimeFormat();
                File file = new File(FILE_NAME + time + FILE_NAME_SUFFIX);
                PrintWriter printWriter = null;
                try {
                    printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                    printWriter.println(time);
                    dumpPhoneInfo(printWriter);
                    printWriter.println();
                    throwable.printStackTrace(printWriter);
                } finally {
                    IoUtil.closeQuietly(printWriter);
                }
            }
        } else {
            LogUtil.d(LOG_TAG, "write external storage permission denied!");
        }
    }

    /**
     * 获取手机信息
     */
    private void dumpPhoneInfo(PrintWriter printWriter) {
        printWriter.println("App Version: " + AppUtil.getVersionName(mContext) + "_" + AppUtil.getVersionCode(mContext));

        printWriter.println("OS Version: " + AndroidVersionUtil.getCurrentOSVersion() + "_" + AndroidVersionUtil.getCurrentSDKInt());

        printWriter.println("Vendor: " + Build.MANUFACTURER);

        printWriter.println("Model: " + Build.MODEL);

        printWriter.println("CUP ABI: " + Build.CPU_ABI);
    }

    private void uploadException2Server(Throwable throwable) {
        // TODO upload Exception to server
    }
}
