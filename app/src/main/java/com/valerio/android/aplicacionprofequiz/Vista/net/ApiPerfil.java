package com.valerio.android.aplicacionprofequiz.Vista.net;

import com.valerio.android.aplicacionprofequiz.Vista.modelo.Perfil;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Top;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiPerfil {

    @GET("Aaron/profe.php")
  Call<List<Perfil>> getPerfils();
}
