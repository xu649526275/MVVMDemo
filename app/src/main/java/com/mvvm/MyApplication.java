package com.mvvm;

import android.content.Context;
import com.arms.mvvm.BaseApplication;
import com.arms.mvvm.view.refresh.SmartRefreshLayout;
import com.arms.mvvm.view.refresh.api.DefaultRefreshHeaderCreator;
import com.arms.mvvm.view.refresh.api.RefreshHeader;
import com.arms.mvvm.view.refresh.api.RefreshLayout;
import com.arms.mvvm.view.refresh.constant.SpinnerStyle;
import com.arms.mvvm.view.refresh.header.ClassicsHeader;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2018/7/24 0024
 * @Modify:
 */


public class MyApplication extends BaseApplication{


    public static MyApplication sApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
    }



    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public static MyApplication getApplication(){
        return sApplication;
    }


}
