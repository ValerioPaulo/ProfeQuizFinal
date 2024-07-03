package com.valerio.android.aplicacionprofequiz.Vista.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //Esta clase establece el llamado a la api ademas de traer y convertir los datos json al objeto mas adelante
    private static Retrofit retrofit;
    public static  Retrofit getClient(){
        retrofit= new Retrofit.Builder()
                .baseUrl("https://profequiz.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
