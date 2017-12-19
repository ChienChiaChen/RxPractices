package com.chiachen.rxjavapractices.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chiachen.rxjavapractices.CommonUtils;
import com.chiachen.rxjavapractices.R;
import com.chiachen.rxjavapractices.network.NetworkWrapper;
import com.chiachen.rxjavapractices.network.api.Api;
import com.chiachen.rxjavapractices.network.login.LoginResponse;
import com.chiachen.rxjavapractices.network.register.RegisterRequest;
import com.chiachen.rxjavapractices.network.register.RegisterResponse;
import com.chiachen.rxjavapractices.utils.rx.AppSchedulerProvider;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

/**
 * Created by jianjiacheng on 18/12/2017.
 */

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = ((EditText)findViewById(R.id.account)).getText().toString();
                String pwd = ((EditText) findViewById(R.id.pwd)).getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "NO_DATA", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = NetworkWrapper.create();
                final Api api = retrofit.create(Api.class);

                api.login(account,pwd)
                        .subscribeOn(AppSchedulerProvider.io())
                        .observeOn(AppSchedulerProvider.ui())
                        .subscribe(new Observer<List<LoginResponse>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<LoginResponse> loginResponses) {

                            }

                            @Override
                            public void onError(Throwable e) {
                                CommonUtils.showToast(LoginActivity.this, "Login Failed");
                            }

                            @Override
                            public void onComplete() {
                                CommonUtils.showToast(LoginActivity.this, "Login Success");
                                openHomeActivity();
                            }
                        });
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = ((EditText)findViewById(R.id.account)).getText().toString();
                String pwd = ((EditText) findViewById(R.id.pwd)).getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "NO_DATA", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Post data
                Retrofit retrofit = NetworkWrapper.create();
                final Api api = retrofit.create(Api.class);

                RegisterRequest registerRequest = new RegisterRequest().setName(account).setPwd(pwd);
                api.register(registerRequest)
                        .subscribeOn(AppSchedulerProvider.io())
                        .observeOn(AppSchedulerProvider.ui())
                        .subscribe(new Observer<RegisterResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(RegisterResponse registerResponse) {
                            }

                            @Override
                            public void onError(Throwable e) {
                                CommonUtils.showToast(LoginActivity.this, "Register Failed");

                            }

                            @Override
                            public void onComplete() {
                                CommonUtils.showToast(LoginActivity.this, "Register Success");
                            }
                        });
            }
        });
    }


    private void openHomeActivity() {
        Intent intent = HomeActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }
}
