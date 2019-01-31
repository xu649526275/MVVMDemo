package com.zhy.wanandroid.model;



import com.zhy.wanandroid.bean.BaseResult;
import com.zhy.wanandroid.http.HttpRetrofit;
import com.zhy.wanandroid.http.WanApiService;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseRepository {

    private WanApiService apiService;
    private CompositeSubscription mCompositeSubscription;
    protected abstract String cacheId();


    public BaseRepository() {
        apiService=HttpRetrofit.getInstince().getRetroft().create(WanApiService.class);
    }

    public WanApiService getApiService() {
        return apiService;
    }

    public boolean isSuccess(BaseResult result){
        return result!=null && result.isSuccess();
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
