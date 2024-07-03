package com.valerio.android.aplicacionprofequiz.Vista.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientPerfil {

    private static Retrofit retrofit;
    public static  Retrofit getPerfil(){
        retrofit= new Retrofit.Builder()
                .baseUrl("https://profequiz.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
