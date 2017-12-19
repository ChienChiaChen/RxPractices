package com.chiachen.rxjavapractices.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chiachen.rxjavapractices.R;

/**
 * Created by jianjiacheng on 18/12/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private String account, pwd;
    private Button register, login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1-1 Get data
                // 1-2 Data
                // 1-3
            }
        });
        findViewById(R.id.pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Post data
            }
        });
    }
}
