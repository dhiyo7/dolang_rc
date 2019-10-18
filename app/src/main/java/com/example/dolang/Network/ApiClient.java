package com.example.dolang.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    public static final String ENDPOINT = "http://dolangweb.com/";
    public static final String ENDPOINT = "https://dolang-apps.herokuapp.com/";
//    private static final String BASE_URL = "http://dolangweb.com/api/";
    private static final String BASE_URL = "https://dolang-apps.herokuapp.com/api/";
    private static Retrofit retrofit;

    private static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static TourInterface getApiInterfaceService(){
        return getApiClient().create(TourInterface.class);
    }

    public static UserInterface getUserInterface(){
        return getApiClient().create(UserInterface.class);
    }
}
