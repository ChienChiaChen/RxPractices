package com.chiachen.rxjavapractices.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by jianjiacheng on 2018/05/21.
 */

public interface SchedulerProvider {
    Scheduler ui();
    Scheduler computation();
    Scheduler io();
}
