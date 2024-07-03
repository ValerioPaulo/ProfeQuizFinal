package com.valerio.android.aplicacionprofequiz.Vista;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://profequiz.000webhostapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }




}
