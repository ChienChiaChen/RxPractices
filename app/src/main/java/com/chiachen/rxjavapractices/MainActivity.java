package com.chiachen.rxjavapractices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Jason_Chien";

    View loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                        }
                    })
                    .observeOn(Schedulers.io())
                    .doOnNext(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
                        }
                    })
                    .subscribe(consumer);

        setupUI();
    }

    private void setupUI() {
        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = NetworkWrapper.create();
                Api api = retrofit.create(Api.class);
                api.login("125")
                    .subscribeOn(Schedulers.io())               //Use io thread to process network request
                    .observeOn(AndroidSchedulers.mainThread())  //Back to Main thread
                    .subscribe(new Observer<LoginResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(LoginResponse value) {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });
    }
}
