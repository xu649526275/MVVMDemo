package com.zhy.wanandroid.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.arms.mvvm.base.NetworkRepository;
import com.arms.mvvm.utils.cache.CacheUtils;
import com.arms.mvvm.utils.Resource;
import com.arms.mvvm.utils.taskscheduler.TaskScheduler;
import com.zhy.wanandroid.bean.BannerListBean;
import com.zhy.wanandroid.bean.WanBean;


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


public class WanRepository extends BaseRepository {



    public WanRepository() {
        super();
    }

    @Override
    protected String cacheId() {
        return "wan_list";
    }



    public LiveData<Resource<WanBean>> getArticleListInit(int curPage){
        return new NetworkRepository<WanBean>(){

            @Override
            protected boolean shouldCache() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<WanBean> loadFromDb() {
                MediatorLiveData<WanBean> liveData=new MediatorLiveData<>();
                TaskScheduler.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            WanBean dataBeanBaseResult= (WanBean) CacheUtils.getInstance().getSerializable(cacheId());
                            liveData.postValue(dataBeanBaseResult);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                return liveData;
            }



            @NonNull
            @Override
            protected LiveData<Resource<WanBean>> createCall() {
                return getArticleList(curPage,true);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<WanBean>> getArticleList(int curPage, boolean addCache){
        MediatorLiveData<Resource<WanBean>> liveData=new MediatorLiveData<>();
        addSubscription(getApiService().getArticlesList(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Action1<WanBean>() {
                    @Override
                    public void call(WanBean dataBeanBaseResult) {
                        if(addCache){
                            Schedulers.io().createWorker().schedule(new Action0() {
                                @Override
                                public void call() {
                                    CacheUtils.getInstance().put(cacheId(),dataBeanBaseResult);
                                }
                            });
                        }
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WanBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(Resource.error(null,null));
                    }

                    @Override
                    public void onNext(WanBean dataBeanBaseResult) {
                        if(dataBeanBaseResult.getData()==null||dataBeanBaseResult.getData().getDatas().isEmpty()){
                            liveData.setValue(Resource.empty());
                        }else{
                            liveData.setValue(Resource.success(dataBeanBaseResult));
                        }
                    }
                }));
        return liveData;
    }






    public LiveData<Resource<BannerListBean>> getBannerInit(){
        return new NetworkRepository<BannerListBean>(){

            @Override
            protected boolean shouldCache() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<BannerListBean> loadFromDb() {
                MediatorLiveData<BannerListBean> liveData=new MediatorLiveData<>();
                TaskScheduler.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BannerListBean dataBeanBaseResult= (BannerListBean) CacheUtils.getInstance().getSerializable("banner_list");
                            liveData.postValue(dataBeanBaseResult);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                return liveData;
            }



            @NonNull
            @Override
            protected LiveData<Resource<BannerListBean>> createCall() {
                return getBannerList();
            }
        }.getAsLiveData();
    }




    private LiveData<Resource<BannerListBean>> getBannerList(){
        MediatorLiveData<Resource<BannerListBean>> liveData=new MediatorLiveData<>();
        addSubscription(getApiService().getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Action1<BannerListBean>() {
                    @Override
                    public void call(BannerListBean dataBeanBaseResult) {
                        Schedulers.io().createWorker().schedule(new Action0() {
                            @Override
                            public void call() {
                                CacheUtils.getInstance().put("banner_list",dataBeanBaseResult);
                            }
                        });
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BannerListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(Resource.error(null,null));
                    }

                    @Override
                    public void onNext(BannerListBean dataBeanBaseResult) {
                        if(dataBeanBaseResult==null){
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
