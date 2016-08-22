package com.jackfangqi.commonutil.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/22 10:15
 */
public class ImageResizer {
    private static final String LOG_TAG = ImageResizer.class.getSimpleName();

    public ImageResizer() {
    }

    public Bitmap decodeSampledBitmapFromRes(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSmapleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        options.inSampleSize = calculateInSmapleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    private int calculateInSmapleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0)
            return 1;

        int width = options.outWidth;
        int height = options.outHeight;
        LogUtil.d(LOG_TAG, "origin: width = " + width + ", height = " + height);
        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;
            while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        }

        LogUtil.d(LOG_TAG, "final inSampleSize = " + inSampleSize);
        return inSampleSize;
    }
}
