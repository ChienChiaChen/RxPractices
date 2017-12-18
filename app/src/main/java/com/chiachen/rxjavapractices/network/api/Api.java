package com.chiachen.rxjavapractices.network.api;



import com.chiachen.rxjavapractices.network.login.LoginResponse;
import com.chiachen.rxjavapractices.network.register.RegisterRequest;
import com.chiachen.rxjavapractices.network.register.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jianjiacheng on 12/12/2017.
 */

public interface Api {
    // It's local host
    String BASE_URL = "http://192.168.0.118:3000/";

    /**
     *  To login
     * @param newId it's member id
     * @return LogResponse
     */
    @GET("members/{id}")
    Observable<LoginResponse> login(@Path("id") String newId);

    @POST("members")
    @Headers("Content-Type: application/json")
    Observable<RegisterResponse> register(@Body RegisterRequest request);
}
