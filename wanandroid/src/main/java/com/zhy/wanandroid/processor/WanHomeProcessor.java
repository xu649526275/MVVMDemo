/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-8-2 下午5:07
 *
 *
 */

package com.zhy.wanandroid.processor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.arms.mvvm.cc.ComponentConstant;
import com.arms.mvvm.cc.IActionProcessor;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.zhy.wanandroid.ui.WanHomeActivity;
import com.zhy.wanandroid.ui.fragment.WanFragment;

import java.util.Map;

/**
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang@ebrun.com
 * @Version: V5.1.0
 * @Create: 2018/8/2 17:07
 * @Modify:
 */
public class WanHomeProcessor implements IActionProcessor {
    @Override
    public String getActionName() {
        return ComponentConstant.WAN_COMPONENT_MAIN;
    }

    @Override
    public boolean onActionCall(CC cc) {
        Log.d("DaLong_CC_WanHome","组件化成功进入WanHome");
        CC.sendCCResult(cc.getCallId(), CCResult.success("fragment",new WanFragment()));
        return false;
    }
}
