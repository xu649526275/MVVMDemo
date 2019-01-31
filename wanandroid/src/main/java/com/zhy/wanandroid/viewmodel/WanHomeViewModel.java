package com.zhy.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arms.mvvm.base.BaseViewModel;
import com.arms.mvvm.utils.Resource;
import com.zhy.wanandroid.bean.WanBean;
import com.zhy.wanandroid.model.WanRepository;


/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2018/7/24 0024
 * @Modify:
 */


public class WanHomeViewModel extends BaseViewModel implements LifecycleObserver{

    WanRepository mainModel;


    public WanHomeViewModel(@NonNull Application application) {
        super(application);
        this.mainModel=new WanRepository();

    }

    public LiveData<Resource<WanBean>> getArticleInit(int cPage){
        return mainModel.getArticleListInit(1);
    }


    public LiveData<Resource<WanBean>> getArticleList(int cPage){
        return mainModel.getArticleList(1,false);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if(mainModel!=null){
            mainModel.clear();
        }
        Log.d("DaLong_ViewModel","ViewModel关闭");
    }






}
