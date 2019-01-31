/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-8-2 下午5:07
 *
 *
 */

package com.mvvm.processor;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.arms.mvvm.cc.ComponentConstant;
import com.arms.mvvm.cc.IActionProcessor;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;

import java.util.Map;

/**
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang@ebrun.com
 * @Version: V5.1.0
 * @Create: 2018/8/2 17:07
 * @Modify:
 */
public class MainProcessor implements IActionProcessor {
    @Override
    public String getActionName() {
        return ComponentConstant.APP_COMPONENT_MAIN;
    }

    @Override
    public boolean onActionCall(CC cc) {
        Context context = cc.getContext();
        Map<String, Object> params = cc.getParams();
        Log.d("DaLong","我是CC框架的结果");
        Toast.makeText(context,(String)params.get("toast"),Toast.LENGTH_LONG).show();
        CC.sendCCResult(cc.getCallId(), CCResult.success());
        return false;
    }
}
