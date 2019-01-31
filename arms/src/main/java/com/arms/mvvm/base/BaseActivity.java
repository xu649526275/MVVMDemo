package com.arms.mvvm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;



/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2018/7/25 0025
 * @Modify:
 */


public abstract class BaseActivity  extends AppCompatActivity {

    public String TAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getSimpleName();
        initView();
        initData();
    }


    public Context getContext(){
        return this;
    }

    public abstract void initView();

    public abstract void initData();
}
