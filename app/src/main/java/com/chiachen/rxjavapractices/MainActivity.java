package com.chiachen.rxjavapractices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Jason_Chien";

    View loginBtn, registerBtn, map, flapMap;
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
                login();
            }
        });

        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map();
            }
        });

        flapMap = findViewById(R.id.flatMap);
        flapMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flatMap();
            }
        });

        findViewById(R.id.concatMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concatMap();
            }
        });

        findViewById(R.id.register_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegisterLogin();
            }

            private void attemptRegisterLogin() {
                Retrofit retrofit = NetworkWrapper.create();
                final Api api = retrofit.create(Api.class);
                api.register(new RegisterRequest().setName("jason hi"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<RegisterResponse>() {
                            @Override
                            public void accept(RegisterResponse registerResponse) throws Exception {
                                if (null != registerResponse) {
                                    Log.e("JASON_CHIEN", "Register success");
                                    Log.e("JASON_CHIEN", "RegisterResponse id: "+registerResponse.getId());
                                    Log.e("JASON_CHIEN", "RegisterResponse name: "+registerResponse.getName());
                                }
                            }
                        })
                        .observeOn(Schedulers.io())
                        .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
                            @Override
                            public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
                                return api.login(registerResponse.getId());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<LoginResponse>() {
                            @Override
                            public void accept(LoginResponse loginResponse) throws Exception {
                                Log.e("JASON_CHIEN", "processing");
                            }
                        })
                        .subscribe(new Observer<LoginResponse>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.e("JASON_CHIEN", "onSubscribe");
                            }

                            @Override
                            public void onNext(@NonNull LoginResponse loginResponse) {
                                Log.e("JASON_CHIEN", "onNext");
                                Log.e("JASON_CHIEN", "LoginResponse id: "+loginResponse.getId());
                                Log.e("JASON_CHIEN", "LoginResponse name: "+loginResponse.getName());
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("JASON_CHIEN", "onError");
                            }

                            @Override
                            public void onComplete() {
                                Log.e("JASON_CHIEN", "onComplete");
                            }
                        });

            }
        });

        findViewById(R.id.zip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                        Log.e("JASON_CHIEN", "Current Thread: "+Thread.currentThread().getName());

                        Log.e("JASON_CHIEN", "observable1 emit "+1);
                        e.onNext(1);
                        Thread.sleep(1000);

                        Log.e("JASON_CHIEN", "observable1 emit "+2);
                        e.onNext(2);
                        Thread.sleep(1000);

                        Log.e("JASON_CHIEN", "observable1 emit "+3);
                        e.onNext(3);
                        Thread.sleep(1000);

                        // Log.e("JASON_CHIEN", "observable1 emit "+4);
                        // e.onNext(4);
                        // Thread.sleep(1000);

                        Log.e("JASON_CHIEN", "observable1 complete ");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io());

                Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                        Log.e("JASON_CHIEN", "Current Thread: "+Thread.currentThread().getName());

                        Log.e("JASON_CHIEN", "observable2 emit A");
                        e.onNext("A");
                        Thread.sleep(1000);
                        Log.e("JASON_CHIEN", "observable2 emit B");
                        e.onNext("B");
                        Thread.sleep(1000);
                        Log.e("JASON_CHIEN", "observable2 emit C");
                        e.onNext("C");
                        Thread.sleep(1000);
                        Log.e("JASON_CHIEN", "observable2 emit D");
                        e.onNext("D");
                        Log.e("JASON_CHIEN", "observable2 complete ");
                        e.onComplete();

                    }
                }).subscribeOn(Schedulers.io());

                Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(@NonNull Integer i, @NonNull String s) throws Exception {
                        return s+i;
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("JASON_CHIEN", "accept: " + s);
                    }
                });
            }
        });

        findViewById(R.id.zip_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZipExample.getInstance().request();
            }
        });
    }

    private void concatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e("JASON_CHIEN", "emit: 1");
                e.onNext(1);
                Log.e("JASON_CHIEN", "emit: 2");
                e.onNext(2);
                Log.e("JASON_CHIEN", "emit: 3");
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i=0;i<3;i++) {
                    list.add("concatMap: " + integer);
                }
                return Observable.fromIterable(list).delay(5, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("JASON_CHIEN", s);
            }
        });
    }

    private void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e("JASON_CHIEN", "emit: 1");
                e.onNext(1);
                Log.e("JASON_CHIEN", "emit: 2");
                e.onNext(2);
                Log.e("JASON_CHIEN", "emit: 3");
                e.onNext(3);
                Log.e("JASON_CHIEN", "emit: 4");
                e.onNext(4);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i=0;i<3;i++) {
                    list.add("flatMap: " + integer);
                }
                return Observable.fromIterable(list).delay(5, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("JASON_CHIEN", s);
            }
        });
    }

    private void map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e("JASON_CHIEN", "emit: 1");
                e.onNext(1);
                Log.e("JASON_CHIEN", "emit: 2");
                e.onNext(2);
                Log.e("JASON_CHIEN", "emit: 3");
                e.onNext(3);
                Log.e("JASON_CHIEN", "emit: 4");
                e.onNext(4);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "map: " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("JASON_CHIEN", s);
            }
        });
    }

    private void login() {
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


    private void register() {
        Retrofit retrofit = NetworkWrapper.create();
        Api api = retrofit.create(Api.class);
        RegisterRequest registerRequest = new RegisterRequest().setName("ee");
        api.register(registerRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegisterResponse registerResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MainActivity.this, "Register fail", Toast.LENGTH_SHORT).show();
                        Log.e("JASON_CHIEN", "Register fail");
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                        Log.e("JASON_CHIEN", "Register success");
                    }
                });
    }
}
