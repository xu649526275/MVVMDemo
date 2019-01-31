package com.arms.mvvm;

import android.app.Application;

public class Common {
    public static Application sApplication;

    public static void init(Application application) {
        sApplication = application;
//        initNetState();
//        initDataRes();
//        application.registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
    }


    public static Application getApplication() {
        return sApplication;
    }
}
