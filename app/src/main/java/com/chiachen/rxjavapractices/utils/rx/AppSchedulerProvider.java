package com.chiachen.rxjavapractices.utils.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jianjiacheng on 20/12/2017.
 */

public class AppSchedulerProvider  {

    public static Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    public static Scheduler computation() {
        return Schedulers.computation();
    }

    public static Scheduler io() {
        return Schedulers.io();
    }
}
