package com.zhy.wanandroid.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRetrofit {
    public static final  String BASE_URL = "http://www.wanandroid.com/";

    private static HttpRetrofit instance;


    public static HttpRetrofit  getInstince() {
        if(instance==null){
            synchronized (HttpRetrofit.class){
                if(instance==null){
                    instance=new HttpRetrofit();
                }
            }
        }
        return instance;
    }


    public  Retrofit getRetroft(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(initOkHttp())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  Retrofit getRetroft(String base_url){
        return new Retrofit.Builder()
                .baseUrl(base_url)
                .client(initOkHttp())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    public static OkHttpClient initOkHttp(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }


}
