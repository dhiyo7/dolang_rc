package com.example.dolang.Network;

import com.example.dolang.Converter.BaseListResponse;
import com.example.dolang.Converter.BaseResponse;
import com.example.dolang.Model.ModelKomentar;
import com.example.dolang.Model.ModelUser;
import com.example.dolang.Model.Tour;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TourInterface {

    @GET("tours")
    Call<BaseListResponse<Tour>> getTour();

//    @FormUrlEncoded
//    @POST("tour")
//    Call<BaseListResponse<ModelKategori>> marker(
//    @Field("longitude") String longitude, @Field("latitude") String latitude);

    @GET("tour/{id}")
    Call<BaseResponse<Tour>> showById(@Path("id") String id);

    @GET("tour/category/{slug}")
    Call<BaseListResponse<Tour>> showByCategory(@Path("slug") String slug);


    @FormUrlEncoded
    @POST("comment")
    Call<BaseResponse<ModelKomentar>> comment(@Header ("Authorization") String api_token, @Field("tour_id") String tour_id, @Field("message") String messages);

    @FormUrlEncoded
    @POST("tour/search")
    Call<BaseListResponse<Tour>> search (@Field("search") String search);
}
