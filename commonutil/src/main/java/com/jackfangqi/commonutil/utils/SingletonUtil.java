package com.jackfangqi.commonutil.utils;

/**
 * Compiler: Android Studio
 * Project: CommonUtils
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/18 18:01
 */
public abstract class SingletonUtil<T> {
    private T mInstance;

    protected abstract T newInstance();

    public final T getInstance() {
        if (mInstance == null) {
            synchronized (SingletonUtil.class) {
                if (mInstance == null)
                    mInstance = newInstance();
            }
        }

        return mInstance;
    }
}
