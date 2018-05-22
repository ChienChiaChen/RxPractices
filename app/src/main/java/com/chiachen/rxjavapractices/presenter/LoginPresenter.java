package com.chiachen.rxjavapractices.presenter;

import android.text.TextUtils;

import com.chiachen.rxjavapractices.network.NetworkWrapper;
import com.chiachen.rxjavapractices.network.api.Api;
import com.chiachen.rxjavapractices.network.login.LoginResponse;
import com.chiachen.rxjavapractices.network.register.RegisterRequest;
import com.chiachen.rxjavapractices.network.register.RegisterResponse;
import com.chiachen.rxjavapractices.utils.rx.SchedulerProvider;
import com.chiachen.rxjavapractices.view.LoginView;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by jianjiacheng on 2018/05/20.
 */

public class LoginPresenter extends BasePresenter {
    private LoginView mLoginView;
    private Api mApi;
    private SchedulerProvider mSchedulerProvider;

    public LoginPresenter(LoginView loginView, Api api, SchedulerProvider schedulerProvider) {
        mLoginView = loginView;
        mApi = api;
        mSchedulerProvider = schedulerProvider;
    }

    public void triggerLogin(String account, String pwd) {
        if ((account == null || account.length() == 0) || (pwd == null || pwd.length() == 0)) {
            mLoginView.showToast("NO DATA");
            return;
        }

        mApi.login(account, pwd)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<List<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LoginResponse> loginResponses) {
                        mLoginView.showToast("Login Success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.isErrorInput();
                    }

                    @Override
                    public void onComplete() {
                        mLoginView.openHomeActivity();
                    }
                });
    }

    public void triggerRegisterAndLogin(final String account, final String pwd) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            mLoginView.showToast("NO DATA");
            return;
        }

        // Post data
        Retrofit retrofit = NetworkWrapper.create();
        final Api api = retrofit.create(Api.class);

        RegisterRequest registerRequest = new RegisterRequest().setName(account).setPwd(pwd);
        api.register(registerRequest)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnNext(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        mLoginView.showToast((null == registerResponse) ? "Register fails" : "Register success");
                    }
                })
                .observeOn(mSchedulerProvider.io())
                .flatMap(new Function<RegisterResponse, ObservableSource<List<LoginResponse>>>() {
                    @Override
                    public ObservableSource<List<LoginResponse>> apply(RegisterResponse registerResponse) throws Exception {
                        return api.login(account, pwd);
                    }
                })
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<List<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LoginResponse> loginResponses) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.isErrorInput();
                    }

                    @Override
                    public void onComplete() {
                        mLoginView.showToast("Login Success");
                        mLoginView.openHomeActivity();
                    }
                });
    }
}
