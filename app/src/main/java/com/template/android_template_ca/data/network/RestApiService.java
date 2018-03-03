package com.template.android_template_ca.data.network;

import com.template.android_template_ca.domain.models.Auth;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RestApiService {

    @FormUrlEncoded
    @POST("login")
    Single<Auth> login(@Field("email") String email,
                       @Field("password") String password);

}
