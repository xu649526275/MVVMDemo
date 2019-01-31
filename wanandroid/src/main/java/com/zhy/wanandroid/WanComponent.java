/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-7-5 下午3:19
 *
 *
 */

package com.zhy.wanandroid;

import com.arms.mvvm.cc.ComponentConstant;
import com.arms.mvvm.cc.IActionProcessor;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Qa component.
 *
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang @ebrun.com
 * @Version: V4.9.0
 * @Create: 2018 /7/5 15:19
 * @Modify:
 */
public class WanComponent implements IComponent {

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private final HashMap<String, IActionProcessor> map = new HashMap<>(4);

    private void initProcessors() {
    }

    private void add(IActionProcessor processor) {
        map.put(processor.getActionName(), processor);

    }

    @Override
    public String getName() {
        return ComponentConstant.WAN_COMPONENT;
    }

    @Override
    public boolean onCall(CC cc) {
        if (initialized.compareAndSet(false, true)) {
            synchronized (map) {
                initProcessors();
            }
        }
        String actionName = cc.getActionName();
        IActionProcessor processor = map.get(actionName);
        if (processor != null) {
            return processor.onActionCall(cc);
        }
        CC.sendCCResult(cc.getCallId(), CCResult.error("has not support for action:" + cc.getActionName()));
        return false;
    }
}
