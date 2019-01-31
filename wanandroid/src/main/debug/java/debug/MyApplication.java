package debug;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.arms.mvvm.BaseApplication;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2018/7/24 0024
 * @Modify:
 */


public class MyApplication extends BaseApplication  {


    public static MyApplication sApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
        initDex();
    }

    private void initDex() {
        MultiDex.install(this);
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public static MyApplication getApplication(){
        return sApplication;
    }




}
