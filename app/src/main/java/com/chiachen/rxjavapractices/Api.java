package com.chiachen.rxjavapractices;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jianjiacheng on 12/12/2017.
 */

public interface Api {
    // It's local host
    String BASE_URL = "http://192.168.0.104:3000/";

    /**
     *  To login
     * @param newId it's member id
     * @return LogResponse
     */
    @GET("members/{id}")
    Observable<LoginResponse> login(@Path("id") String newId);
}
