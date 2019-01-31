package com.mvvm.model;


import com.mvvm.http.ApiService;
import com.mvvm.http.HttpRetrofit;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseRepository {

    public static String TAG;
    private ApiService apiService;
    private CompositeSubscription mCompositeSubscription;

    protected abstract String cacheId();

    protected abstract String hostBaseUrl();

    public BaseRepository(){
        TAG=this.getClass().getSimpleName();
        this.apiService = HttpRetrofit.getInstince().getRetroft(hostBaseUrl()).create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }



    public abstract void clear();


    /**
     * 事件订阅
     *
     * @param s
     *         the s
     */
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }


    /**
     * Unsubcrible.
     */
    public void unsubcrible() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
        this.mCompositeSubscription = null;
    }


}
