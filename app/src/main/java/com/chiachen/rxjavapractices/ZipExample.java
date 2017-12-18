package com.chiachen.rxjavapractices;

import android.util.Log;

import com.chiachen.rxjavapractices.network.NetworkWrapper;
import com.chiachen.rxjavapractices.network.api.UserApi;
import com.chiachen.rxjavapractices.network.base_info.UserBaseInfoResponse;
import com.chiachen.rxjavapractices.network.extra_info.UserExtraInfoResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by jianjiacheng on 13/12/2017.
 */

public class ZipExample {
    private final static ZipExample sZipExample = new ZipExample();
    private ZipExample() {
    }

    public static ZipExample getInstance() {
        return sZipExample;
    }

    public void request() {
        Retrofit retrofit = NetworkWrapper.create();
        UserApi userApi = retrofit.create(UserApi.class);
        Observable<UserBaseInfoResponse> observable1 = userApi.getUserBaseInfo("1").subscribeOn(Schedulers.io());
        Observable<UserExtraInfoResponse> observable2 = userApi.getUserExtraInfo("1").subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2,
                new BiFunction<UserBaseInfoResponse, UserExtraInfoResponse, UserInfo>() {
                    @Override
                    public UserInfo apply(UserBaseInfoResponse baseInfo, UserExtraInfoResponse extraInfo) throws Exception {
                        return new UserInfo(baseInfo, extraInfo);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        //do something;
                        Log.e("JASON_CHIEN", userInfo.getBaseInfoResponse().getId());
                        Log.e("JASON_CHIEN", userInfo.getBaseInfoResponse().getName());
                        Log.e("JASON_CHIEN", userInfo.getExtraInfoResponse().getId());
                        Log.e("JASON_CHIEN", userInfo.getExtraInfoResponse().getAge());

                        Log.e("JASON_CHIEN", "Acccpt");
                    }
                });
    }

    private static  class UserInfo {
        private final UserBaseInfoResponse mBaseInfoResponse;
        private final UserExtraInfoResponse mExtraInfoResponse;

        public UserInfo(UserBaseInfoResponse baseInfoResponse, UserExtraInfoResponse extraInfoResponse) {
            mBaseInfoResponse = baseInfoResponse;
            mExtraInfoResponse = extraInfoResponse;
        }

        public UserBaseInfoResponse getBaseInfoResponse() {
            return mBaseInfoResponse;
        }

        public UserExtraInfoResponse getExtraInfoResponse() {
            return mExtraInfoResponse;
        }
    }
}
