package com.example.dolang.Network;

import com.example.dolang.Converter.BaseResponse;
import com.example.dolang.Model.ModelUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInterface {

    @FormUrlEncoded
    @POST("login")
    Call<BaseResponse<ModelUser>> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<BaseResponse<ModelUser>> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);


}
