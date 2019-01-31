/*
 *  Copyright ® 2017.   All right reserved.
 *
 *  Last modified 17-2-14 下午3:00
 *
 *
 */

package com.arms.mvvm.utils.rx;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * The type Rx bus.
 *
 * @Description: 用RxJava实现的EventBus
 * @Author: xuyangyang
 * @Email: xuyangyang @ebrun.com
 * @Version: V4.4.0
 * @Create: 2017 /11/6 15:02
 * @Modify:
 */
public class RxBus {
    private static RxBus instance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized RxBus getInstance() {
        if (null == instance) {
            instance = new RxBus();
        }
        return instance;
    }

    private RxBus() {
    }

    @SuppressWarnings("rawtypes")
    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<Object, List<Subject>>();

    /**
     * 订阅事件源
     *
     * @param mObservable
     *         the m observable
     * @param mAction1
     *         the m action 1
     * @return rx bus
     */
    public RxBus onEvent(Observable<?> mObservable, Action1<Object> mAction1) {
        mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(mAction1, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        return getInstance();
    }

    /**
     * 注册事件源
     *
     * @param <T>
     *         the type parameter
     * @param tag
     *         the tag
     * @return observable
     */
    @SuppressWarnings({"rawtypes"})
    public <T> Observable<T> register(@NonNull Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<Subject>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    /**
     * 判断是否注册
     *
     * @param tag
     *         the tag
     * @return boolean
     */
    public boolean judeRegister(@NonNull Object tag){
        List<Subject> subjectList = subjectMapper.get(tag);
        return subjectList != null && !subjectList.isEmpty();
    }

    /**
     * Unregister.
     *
     * @param tag
     *         the tag
     */
    @SuppressWarnings("rawtypes")
    public void unregister(@NonNull Object tag) {
        List<Subject> subjects = subjectMapper.get(tag);
        if (null != subjects) {
            subjectMapper.remove(tag);
        }
    }

    /**
     * 取消监听
     *
     * @param tag
     *         the tag
     * @param observable
     *         the observable
     * @return rx bus
     */
    @SuppressWarnings("rawtypes")
    public RxBus unregister(@NonNull Object tag,
                            @NonNull Observable<?> observable) {
        if (null == observable) {
            return getInstance();
        }
        List<Subject> subjects = subjectMapper.get(tag);
        if (null != subjects) {
            subjects.remove(observable);
            if (isEmpty(subjects)) {
                subjectMapper.remove(tag);
            }
        }
        return getInstance();
    }

    /**
     * Post.
     *
     * @param content
     *         the content
     */
    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    /**
     * 触发事件
     *
     * @param tag
     *         the tag
     * @param content
     *         the content
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    /**
     * Is empty boolean.
     *
     * @param collection
     *         the collection
     * @return the boolean
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection<Subject> collection) {
        return null == collection || collection.isEmpty();
    }

}
