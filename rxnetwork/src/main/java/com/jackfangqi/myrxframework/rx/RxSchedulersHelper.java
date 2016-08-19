package com.jackfangqi.myrxframework.rx;

import android.util.Log;

import com.jackfangqi.myrxframework.model.BaseModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Compiler: Android Studio
 * Project: MyRxFramework
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/12 9:56
 */
public class RxSchedulersHelper {
    private static final String TAG = "RxSchedulersHelper";

    public static <T> Observable.Transformer<BaseModel<T>, T> io_main() {
        return new Observable.Transformer<BaseModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseModel<T>> baseModelObservable) {
                return baseModelObservable.flatMap(new Func1<BaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseModel<T> tBaseModel) {
                        Log.d(TAG, "result from server: " + tBaseModel);
                        if (tBaseModel.success()) {
                            return createData(tBaseModel.getData());
                        } else {
                            return Observable.error(new ServerException(tBaseModel.getMsg()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
