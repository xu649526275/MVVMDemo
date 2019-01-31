/*
 *  Copyright ® 2017.   All right reserved.
 *
 *  Last modified 17-5-31 上午10:45
 *
 *
 */

package com.arms.mvvm.view.statusbar.barlibrary;

import android.support.v4.app.Fragment;

/**
 * ImmersionFragment沉浸式基类，因为fragment是基于activity之上的，
 * 为了能够在fragment使用沉浸式而fragment之间又相互不影响，必须实现immersionInit方法，
 * 原理是当用户可见才执行沉浸式初始化
 *
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang @ebrun.com
 * @Version: V4.4.0
 * @Create: 2017 /11/6 15:04
 * @Modify:
 */
@Deprecated
public abstract class ImmersionFragment extends Fragment {
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && immersionEnabled()) {
            immersionInit();
        }
    }

    /**
     * 当前页面Fragment支持沉浸式初始化。子类可以重写返回false，设置不支持沉浸式初始化
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    @Deprecated
    protected boolean immersionEnabled() {
        return true;
    }

    @Deprecated
    protected abstract void immersionInit();
}
