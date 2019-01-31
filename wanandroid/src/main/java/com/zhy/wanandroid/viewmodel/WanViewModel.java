package com.zhy.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.arms.mvvm.base.BaseViewModel;
import com.arms.mvvm.utils.Resource;
import com.arms.mvvm.utils.Status;
import com.zhy.wanandroid.bean.BannerListBean;
import com.zhy.wanandroid.bean.WanBean;
import com.zhy.wanandroid.model.WanRepository;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2019/1/24 0024
 * @Modify:
 */

public class WanViewModel extends BaseViewModel {

    WanRepository mainModel;


    public WanViewModel(@NonNull Application application) {
        super(application);
        this.mainModel=new WanRepository();
    }


    public LiveData<Resource<WanBean>> getArticleInit(){
        MediatorLiveData<Resource<WanBean>> result=new MediatorLiveData<>();
        result.addSource(mainModel.getArticleListInit(1),newData->{
            loadingStatus.set(newData.status);
            if(newData.status==Status.SUCCESS){
                result.setValue(newData);
            }
        });
        return result;
    }


    public LiveData<Resource<WanBean>> getArticleList(int cPage){
        return mainModel.getArticleList(cPage,false);
    }

    public LiveData<Resource<BannerListBean>> getBannerInit(){
        return mainModel.getBannerInit();
    }


    
}
