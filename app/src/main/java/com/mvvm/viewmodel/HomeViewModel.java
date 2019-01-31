package com.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.arms.mvvm.base.BaseViewModel;
import com.arms.mvvm.utils.Resource;
import com.arms.mvvm.utils.Status;
import com.mvvm.bean.GankBean;
import com.mvvm.model.HomeRepository;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2019/1/24 0024
 * @Modify:
 */

public class HomeViewModel extends BaseViewModel {

    HomeRepository mainModel;


    public HomeViewModel(@NonNull Application application) {
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
            if(newData.status==Status.SUCCESS){
                result.setValue(newData);
            }
        });
        return result;
    }

    /**
     * 下拉刷新
     * */
    public LiveData<Resource<GankBean>> getEbrunRefresh(){
        return mainModel.getEbrunList(1,true);
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
    }

}
