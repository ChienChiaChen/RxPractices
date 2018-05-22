package com.chiachen.rxjavapractices.presenter;

import com.chiachen.rxjavapractices.network.api.Api;
import com.chiachen.rxjavapractices.network.login.LoginResponse;
import com.chiachen.rxjavapractices.scheduler.TrampolineSchedulerProvider;
import com.chiachen.rxjavapractices.utils.rx.SchedulerProvider;
import com.chiachen.rxjavapractices.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by jianjiacheng on 2018/05/21.
 */
public class LoginPresenterTest {

    @Mock
    private LoginView mLoginView;

    @Mock
    private Api mApi;

    @Mock
    private List<LoginResponse> mLoginResponses;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        SchedulerProvider mSchedulerProvider = new TrampolineSchedulerProvider();
        mLoginPresenter = new LoginPresenter(mLoginView, mApi, mSchedulerProvider);
    }

    @Test
    public void triggerLogin_success() throws Exception {
        Mockito
                .doReturn(Observable.just(mLoginResponses))
                .when(mApi).
                login("My","Soul");

        mLoginPresenter.triggerLogin("My","Soul");
        Mockito.verify(mLoginView).openHomeActivity();
    }

}