package com.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arms.mvvm.base.BaseViewModel;
import com.arms.mvvm.utils.Resource;
import com.mvvm.bean.GankBean;
import com.mvvm.model.HomeRepository;



/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2018/7/24 0024
 * @Modify:
 */


public class MainViewModel extends BaseViewModel implements LifecycleObserver{



    HomeRepository mainModel;



    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mainModel=new HomeRepository();
    }

    /**
     * 初始化
     * */
    public LiveData<Resource<GankBean>> getGankInit(){
        MediatorLiveData<Resource<GankBean>> result=new MediatorLiveData<>();
        result.addSource(mainModel.getEbrunInit(),newData->{
            loadingStatus.set(newData.status);
            result.setValue(newData);
        });
        return result;
    }

    /**
     * 下拉刷新
     * */
    public LiveData<Resource<GankBean>> getEbrunRefresh(){
        return mainModel.getEbrunList(1,false);
    }

    /**
     * 加载更多
     * */
    public LiveData<Resource<GankBean>> getEbrunMore(int num){
        return mainModel.getEbrunList(num,false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(mainModel!=null){
            mainModel.clear();
        }
        Log.d("DaLong_ViewModel","ViewModel关闭");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onStart(){
        Log.d("DaLong","View生命周期Create");
    }



}
