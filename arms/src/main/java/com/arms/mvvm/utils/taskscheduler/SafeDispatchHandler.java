/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-6-28 下午1:17
 *
 *
 */

package com.arms.mvvm.utils.taskscheduler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.arms.mvvm.BuildConfig;


/**
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang@ebrun.com
 * @Version: V5.0.0
 * @Create: 2018/6/28 13:17
 * @Modify:
 */
public class SafeDispatchHandler extends Handler {

    private static final String TAG = "SafeDispatchHandler";
    public SafeDispatchHandler(Looper looper) {
        super(looper);
    }

    public SafeDispatchHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public SafeDispatchHandler() {
        super();
    }

    public SafeDispatchHandler(Callback callback) {
        super(callback);
    }

    @Override
    public void dispatchMessage(Message msg) {
        if (BuildConfig.DEBUG) {
            super.dispatchMessage(msg);
        } else {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                Log.e(TAG, "dispatchMessage Exception " + msg + " , " + e);
            } catch (Error error) {
                Log.e(TAG, "dispatchMessage error " + msg + " , " + error);
            }
        }
    }
}