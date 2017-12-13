package com.chiachen.rxjavapractices;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jianjiacheng on 13/12/2017.
 */

public interface UserApi {
    @GET("basic/{id}")
    Observable<UserBaseInfoResponse> getUserBaseInfo(@Path("id")String newId);

    @GET("extra/{id}")
    Observable<UserExtraInfoResponse> getUserExtraInfo(@Path("id")String newId);
}