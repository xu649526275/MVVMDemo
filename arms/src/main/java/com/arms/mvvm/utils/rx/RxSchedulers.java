/*
 *  Copyright ® 2017.   All right reserved.
 *
 *  Last modified 17-2-14 下午2:58
 *
 *
 */

package com.arms.mvvm.utils.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The type Rx schedulers.
 *
 * @Description: RxJava调度管理
 * @Author: xuyangyang
 * @Email: xuyangyang @ebrun.com
 * @Version: V4.4.0
 * @Create: 2017 /11/6 15:02
 * @Modify:
 */
public class RxSchedulers {
    /**
     * Io main observable . transformer.
     *
     * @param <T>
     *         the type parameter
     * @return the observable . transformer
     */
    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
