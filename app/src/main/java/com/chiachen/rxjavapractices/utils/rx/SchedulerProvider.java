package com.chiachen.rxjavapractices.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by jianjiacheng on 20/12/2017.
 */

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
