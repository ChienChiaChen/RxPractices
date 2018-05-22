package com.chiachen.rxjavapractices.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.chiachen.rxjavapractices.CommonUtils;
import com.chiachen.rxjavapractices.R;
import com.chiachen.rxjavapractices.network.NetworkWrapper;
import com.chiachen.rxjavapractices.network.api.Api;
import com.chiachen.rxjavapractices.presenter.LoginPresenter;
import com.chiachen.rxjavapractices.utils.rx.AppSchedulerProvider;
import com.chiachen.rxjavapractices.view.LoginView;

import retrofit2.Retrofit;

/**
 * Created by jianjiacheng on 18/12/2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Retrofit retrofit = NetworkWrapper.create();
        final Api api = retrofit.create(Api.class);
        mPresenter = new LoginPresenter(this, api, new AppSchedulerProvider());

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String account = ((EditText)findViewById(R.id.account)).getText().toString();
                    String pwd = ((EditText) findViewById(R.id.pwd)).getText().toString();
                    mPresenter.triggerLogin(account, pwd);
                }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = ((EditText)findViewById(R.id.account)).getText().toString();
                final String pwd = ((EditText) findViewById(R.id.pwd)).getText().toString();
                mPresenter.triggerRegisterAndLogin(account, pwd);
            }
        });
    }

    public void openHomeActivity() {
        Intent intent = HomeActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showToast(String msg) {
        CommonUtils.showToast(this, msg);
    }

    @Override
    public void isErrorInput() {

    }
}
