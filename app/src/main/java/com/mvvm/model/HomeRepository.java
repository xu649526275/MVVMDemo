package com.mvvm.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.arms.mvvm.base.NetworkRepository;
import com.arms.mvvm.utils.taskscheduler.TaskScheduler;
import com.arms.mvvm.utils.cache.CacheUtils;
import com.arms.mvvm.utils.Resource;
import com.mvvm.bean.GankBean;
import com.mvvm.http.HttpRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2018/7/24 0024
 * @Modify:
 */


public class HomeRepository extends BaseRepository {


    private int pageSize=20;

    public HomeRepository() {
        super();
    }

    @Override
    protected String cacheId() {
        return "home_list";
    }

    @Override
    protected String hostBaseUrl() {
        return HttpRetrofit.BASE_URL;
    }



    /**
     * 先缓存再请求，
     * */
    public LiveData<Resource<GankBean>> getEbrunInit(){
        return new NetworkRepository<GankBean>(){

            @Override
            protected boolean shouldCache() {
                return true;
            }


            @NonNull
            @Override
            protected LiveData<GankBean> loadFromDb() {
                MediatorLiveData<GankBean> liveData=new MediatorLiveData<>();
                TaskScheduler.execute(new Runnable() {
                    @Override
                    public void run() {
                        GankBean bean= (GankBean) CacheUtils.getInstance().getSerializable(cacheId());
                        try {
                            liveData.postValue(bean);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                return liveData;
            }

            @NonNull
            @Override
            protected LiveData<Resource<GankBean>> createCall() {
                    return getEbrunList(1,true);
            }
        }.getAsLiveData();
    }


    /**
     * 网络请求
     * */
    public LiveData<Resource<GankBean>> getEbrunList(int num,boolean addCache){
        MediatorLiveData<Resource<GankBean>> liveData=new MediatorLiveData<>();
        addSubscription(getApiService().getGirlList(pageSize,num)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Action1<GankBean>() {
                    @Override
                    public void call(GankBean dataBeanBaseResult) {
                        if(addCache){
                            Schedulers.io().createWorker().schedule(new Action0() {
                                @Override
                                public void call() {
                                    if(dataBeanBaseResult!=null||!dataBeanBaseResult.getResults().isEmpty()){
                                        CacheUtils.getInstance().put(cacheId(),dataBeanBaseResult);
                                    }
                                }
                            });
                        }
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(Resource.error(null,null));
                    }

                    @Override
                    public void onNext(GankBean dataBeanBaseResult) {
                        if(dataBeanBaseResult==null||dataBeanBaseResult.getResults().isEmpty()){
                            liveData.setValue(Resource.empty());
                        }else{
                            liveData.setValue(Resource.success(dataBeanBaseResult));
                        }
                    }
                }));
        return liveData;
    }


    @Override
    public void clear() {
        unsubcrible();
    }
}
