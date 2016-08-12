package com.jackfangqi.myrxframework.rx;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jackfangqi.myrxframework.tools.NetworkUtil;
import com.jackfangqi.myrxframework.ui.LoadingDialog;

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
    private String msg;
    private AlertDialog mDialog;

    public RxSubscriber(Context context) {
        this(context, "请稍后……");
    }

    public RxSubscriber(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
    }

    protected boolean showDialog() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (showDialog()) {
            mDialog = new LoadingDialog(mContext).createDialog();
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (!isUnsubscribed())
                        unsubscribe();
                }
            });
            mDialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if (showDialog())
            mDialog.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (showDialog())
            mDialog.dismiss();
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
