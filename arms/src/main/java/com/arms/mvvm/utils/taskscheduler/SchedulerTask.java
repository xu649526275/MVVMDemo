/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-6-28 下午1:18
 *
 *
 */

package com.arms.mvvm.utils.taskscheduler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang@ebrun.com
 * @Version: V5.0.0
 * @Create: 2018/6/28 13:18
 * @Modify:
 */
public abstract class SchedulerTask implements Runnable {

    long periodSecond;
    AtomicBoolean canceled = new AtomicBoolean(false);

    public SchedulerTask(long periodSecond) {
        this.periodSecond = periodSecond;
    }

    public abstract void onSchedule();

    @Override
    public void run() {
        if (!canceled.get()) {
            onSchedule();
        }
    }
}
