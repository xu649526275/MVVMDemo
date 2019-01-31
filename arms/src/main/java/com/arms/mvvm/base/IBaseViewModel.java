/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-11-5 下午1:57
 *
 *
 */

package com.arms.mvvm.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * @description: TODO
 * @author: xuyangyang
 * @email: xuyangyang@ebrun.com
 * @version: V5.6.0
 * @create: 2018/11/5 13:57
 * @org: www.ebrun.com 北京亿商联动国际电子商务股份有限公司
 * @modify:
 */
public interface IBaseViewModel {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();
}
