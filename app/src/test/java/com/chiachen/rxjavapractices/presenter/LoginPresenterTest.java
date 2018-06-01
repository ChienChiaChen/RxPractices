package com.chiachen.rxjavapractices.presenter;

import com.chiachen.rxjavapractices.network.api.Api;
import com.chiachen.rxjavapractices.network.login.LoginResponse;
import com.chiachen.rxjavapractices.scheduler.TrampolineSchedulerProvider;
import com.chiachen.rxjavapractices.utils.rx.SchedulerProvider;
import com.chiachen.rxjavapractices.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by jianjiacheng on 2018/05/21.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Observable.class)
public class LoginPresenterTest {

    @Mock
    private LoginView mLoginView;

    @Mock
    private Api mApi;

    @Mock
    private List<LoginResponse> mLoginResponses;

    private LoginPresenter mLoginPresenter;

    private SchedulerProvider mSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mSchedulerProvider = new TrampolineSchedulerProvider();
        mLoginPresenter = new LoginPresenter(mLoginView, mApi, mSchedulerProvider);
    }

    @Test
    public void triggerLogin_success() throws Exception {
        Mockito.doReturn(Observable.just(mLoginResponses)).when(mApi).login("My", "Soul");
        mLoginPresenter.triggerLogin("My", "Soul");
        Mockito.verify(mLoginView).showToast("Login Success");
        Mockito.verify(mLoginView).openHomeActivity();
    }

    //Ref: https://github.com/anupcowkur/MVPSample.
    @Test
    public void triggerLogin_success_1() throws Exception {
        // Observable productsObservable = PowerMockito.mock(Observable.class);


        //create mocks
        Observable<List<LoginResponse>> observable = (Observable<List<LoginResponse>>) PowerMockito.mock(Observable.class);

        Mockito.when(mApi.login("Test","Test")).thenReturn(observable);
        Mockito.when(observable.subscribeOn(mSchedulerProvider.io())).thenReturn(observable);
        Mockito.when(observable.observeOn(mSchedulerProvider.ui())).thenReturn(observable);

        // define return values
        mLoginPresenter.triggerLogin("Test","Test");

        Mockito.verify(mApi).login("Test","Test");
        Mockito.verify(observable).subscribeOn(mSchedulerProvider.io());
        Mockito.verify(observable).observeOn(mSchedulerProvider.ui());
        Mockito.verify(observable).subscribe(Matchers.<Observer<List<LoginResponse>>>any());
        // Matchers.<Consumer<List<LoginResponse>>>any();
        // Mockito.verify(observable).subscribe();
    }

    @Test
    public void triggerLogin_fail() throws Exception {
        Mockito.doReturn(Observable.error(new Exception())).when(mApi).login("ff", "ff");

        mLoginPresenter.triggerLogin("ff", "ff");
        Mockito.verify(mLoginView).isErrorInput();
    }

}