/*
 *  Copyright ® 2017.   All right reserved.
 *
 *  Last modified 17-2-24 上午11:15
 *
 *
 */

package com.arms.mvvm.utils.rx;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * The type Rx manager.
 *
 * @Description: 用于管理单个presenter的RxBus的事件和Rxjava相关代码的生命周期处理
 * @Author: xuyangyang
 * @Email: xuyangyang @ebrun.com
 * @Version: V4.4.0
 * @Create: 2017 /11/6 15:02
 * @Modify:
 */
public class RxManager {
    /**
     * The M rx bus.
     */
    public RxBus mRxBus = RxBus.getInstance();
    //管理rxbus订阅
    private Map<String, Observable<?>> mObservables = new HashMap<>();
    /*管理Observables 和 Subscribers订阅*/
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    /**
     * RxBus注入监听
     *
     * @param <T>
     *         the type parameter
     * @param eventName
     *         the event name
     * @param action1
     *         the action 1
     */
    public <T>void on(String eventName, Action1<T> action1) {
        Observable<T> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        /*订阅管理*/
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    /**
     * 单纯的Observables 和 Subscribers管理
     *
     * @param m
     *         the m
     */
    public void add(Subscription m) {
        /*订阅管理*/
        mCompositeSubscription.add(m);
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消所有订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除rxbus观察
        }
    }

    /**
     * 发送rxbus
     *
     * @param tag
     *         the tag
     * @param content
     *         the content
     */
    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }

    /**
     * 判断是否已经注册过了
     *
     * @param eventName
     *         the event name
     * @return boolean
     */
    public boolean judeRegister(@NonNull String eventName){
        return mRxBus.judeRegister(eventName);
    }

    /**
     * 取消单个的监听
     *
     * @param eventName
     *         the event name
     */
    public void unregister(@NonNull String eventName){
        mRxBus.unregister(eventName);
    }
}
