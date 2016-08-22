package com.jackfangqi.commonutil.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.LruCache;
import android.widget.ImageView;

import com.jackfangqi.commonutil.utils.cache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/22 9:50
 */

/**
 * 简单的ImageLoader，实现了内存缓存和磁盘缓存
 */
public class SimpleImageLoader {
    private static final String LOG_TAG = SimpleImageLoader.class.getSimpleName();

    public static final int MESSAGE_POST_RESULT = 1;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final int TAG_KEY_URI = 998;
    private static final long DISK_CACHE_SIZE = 20 * 1024 * 1024;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mDiskLruCacheCreated;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "SimpleImageLoader#" + mCount.getAndIncrement());
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), sThreadFactory
    );

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if (uri.equals(result.uri)) {
                imageView.setImageBitmap(result.bitmap);
            }
        }
    };

    private Context mContext;
    private ImageResizer mImageResizer = new ImageResizer();
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private SimpleImageLoader(Context context) {
        this.mContext = context.getApplicationContext();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists())
            diskCacheDir.mkdirs();
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mDiskLruCacheCreated = true;
            } catch (IOException e) {
                LogUtil.d(LOG_TAG, e.getMessage());
            }
        }
    }

    public static SimpleImageLoader build(Context context) {
        return new SimpleImageLoader(context);
    }

    private void addBitmap2MemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null)
            mMemoryCache.put(key, bitmap);
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    public void bindBitmap(String uri, ImageView imageView) {
        bindBitmap(uri, imageView, 0, 0);
    }

    private void bindBitmap(final String uri, final ImageView imageView, final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);

        Bitmap bitmap = loadBitmapFromMemoryCache(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    private Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemoryCache(uri);
        if (bitmap != null)
            return bitmap;

        try {
            bitmap = loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
            if (bitmap != null)
                return bitmap;

            bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
        } catch (IOException e) {
            LogUtil.d(LOG_TAG, e.getMessage());
        }

        if (bitmap == null && !mDiskLruCacheCreated)
            bitmap = loadBitmapFromUrl(uri);

        return bitmap;
    }

    private Bitmap loadBitmapFromUrl(String uri) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            LogUtil.d(LOG_TAG, e.getMessage());
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            IoUtil.close(in);
        }

        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String uri, int reqWidth, int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("cannot visit network from UI thread!");
        }

        if (mDiskLruCache == null)
            return null;

        String key = hashKeyFromUri(uri);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(uri, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }

        return loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
    }

    private Bitmap loadBitmapFromDiskCache(String uri, int reqWidth, int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            LogUtil.d(LOG_TAG, "load bitmap from UI thread, it is not recommended!");
        }

        if (mDiskLruCache == null)
            return null;

        Bitmap bitmap = null;
        String key = hashKeyFromUri(uri);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fd = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(fd, reqWidth, reqHeight);
            if (bitmap != null)
                addBitmap2MemoryCache(key, bitmap);
        }

        return bitmap;
    }

    private Bitmap loadBitmapFromMemoryCache(String uri) {
        String key = hashKeyFromUri(uri);

        return getBitmapFromMemoryCache(key);
    }

    public boolean downloadUrlToStream(String uri, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1)
                out.write(b);

            return true;
        } catch (IOException e) {
            LogUtil.d(LOG_TAG, e.getMessage());
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            IoUtil.close(out);
            IoUtil.close(in);
        }

        return false;
    }

    private String hashKeyFromUri(String uri) {
        String hashKey;
        try {
            hashKey = DigestUtil.md5(uri);
        } catch (Exception e) {
            hashKey = String.valueOf(uri.hashCode());
        }

        return hashKey;
    }

    private long getUsableSpace(File path) {
        if (AndroidVersionUtil.isGingerbreadOrHigher())
            return path.getUsableSpace();

        StatFs statFs = new StatFs(path.getPath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks();
    }

    private File getDiskCacheDir(Context context, String name) {
        boolean externalStorageState = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        String cachePath;
        if (externalStorageState) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + name);
    }

    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }

}
