package com.arms.mvvm.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleObserver;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.arms.mvvm.utils.MLog;
import com.arms.mvvm.utils.Status;

public class BaseViewModel extends AndroidViewModel  implements IBaseViewModel,LifecycleObserver {

    public ObservableField<Status> loadingStatus=new ObservableField<Status>();
    public  String TAG;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        TAG=this.getClass().getSimpleName();
        loadingStatus.set(Status.LOADING);
    }




    public void onNoNetClick(){

    }



    @Override
    public void onCreate() {
        MLog.d("BaseViewModel ------>>> onCreate()");

    }

    @Override
    public void onStart() {
        MLog.d("BaseViewModel ------>>> onStart()");

    }

    @Override
    public void onResume() {
        MLog.d("BaseViewModel ------>>> onResume()");

    }

    @Override
    public void onPause() {
        MLog.d("BaseViewModel ------>>> onPause()");

    }

    @Override
    public void onStop() {
        MLog.d("BaseViewModel ------>>> onStop()");

    }

    @Override
    public void onDestroy() {
        MLog.d("BaseViewModel ------>>> onDestroy()");
    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }

    @Override
    public void onAny() {

    }
}
