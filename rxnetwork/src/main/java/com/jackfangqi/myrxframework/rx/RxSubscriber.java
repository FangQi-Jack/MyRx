package com.jackfangqi.myrxframework.rx;

import android.content.Context;

import com.jackfangqi.commonutil.utils.NetworkUtil;

import rx.Subscriber;

/**
 * Compiler: Android Studio
 * Project: MyRxFramework
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/12 9:12
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public RxSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            _onError("请检查您的网络连接");
        } else if (e instanceof ServerException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败");
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String msg);
}
