package com.jackfangqi.myrxframework.rx;

import com.jackfangqi.commonutil.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Compiler: Android Studio
 * Project: MyRxFramework
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/22 16:20
 */
public class RetrofitUtil {
    private static final String LOG_TAG = RetrofitUtil.class.getSimpleName();

    // TODO change to real base url
    private static String apiBaseUrl = "";

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder retrofit = new Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    private RetrofitUtil() {
        throw new AssertionError();
    }

    public static <S> S createService(Class<S> serviceClass) {
        // TODO add http header
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);
        return retrofit.client(httpClient.build()).build().create(serviceClass);
    }

    public static void changeApiBaseUrl(String newBaseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(newBaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static void checkRetrofitRequest(Request request) {
        if (request != null) {
            String method = request.method();
            Headers headers = request.headers();
            RequestBody body = request.body();
            HttpUrl url = request.url();
            LogUtil.d(LOG_TAG, "request method = " + method
                    + "\nheaders = " + headers.toString()
                    + "\nrequest body = " + body
                    + "\nrequest url = " + url);
        }
    }

    public static void connectTimeOut(long time, TimeUnit timeUnit) {
        httpClient.connectTimeout(time, timeUnit);
    }
}
