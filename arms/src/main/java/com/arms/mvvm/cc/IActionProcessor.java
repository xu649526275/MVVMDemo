/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-9-17 下午4:10
 *
 *
 */

package com.arms.mvvm.cc;

import com.billy.cc.core.component.CC;

/**
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang@ebrun.com
 * @Version: V5.1.0
 * @Create: 2018/9/17 16:10
 * @Modify:
 */
public interface IActionProcessor {
    /**
     * Gets action name.
     *
     * @return the action name
     */
    String getActionName();

    /**
     * action的处理类
     *
     * @param cc
     *         cc
     */
    boolean onActionCall(CC cc);
}

