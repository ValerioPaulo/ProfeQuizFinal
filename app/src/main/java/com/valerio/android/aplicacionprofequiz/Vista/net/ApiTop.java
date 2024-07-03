package com.valerio.android.aplicacionprofequiz.Vista.net;

import com.valerio.android.aplicacionprofequiz.Vista.modelo.Top;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiTop
{

    //Aqui podremmos describir todas las operaciones  q solicitaremos de la api
   //este es el metodo de envio http que nos permitira obtener el json
    @GET("Aaron/top.php")
Call<List<Top>> getTop();
}
