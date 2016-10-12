package com.xiaofei.rxjavax.data;

import com.xiaofei.rxjavax.data.api.ZhuangbiApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：xiaofei
 * 日期：2016/10/12
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public class Network {

    private OkHttpClient mOkHttpClient;

    private Network() {
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Network getInstance() {
        return NetworkHolder.instance;
    }

    public ZhuangbiApi getZhuangbiAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl("http://www.zhuangbi.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(ZhuangbiApi.class);
    }

    private static class NetworkHolder {
        static final Network instance = new Network();
    }
}