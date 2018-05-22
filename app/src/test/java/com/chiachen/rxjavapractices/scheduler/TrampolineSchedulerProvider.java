package com.chiachen.rxjavapractices.scheduler;

import com.chiachen.rxjavapractices.utils.rx.SchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jianjiacheng on 2018/05/21.
 */

public class TrampolineSchedulerProvider implements SchedulerProvider {

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }
}