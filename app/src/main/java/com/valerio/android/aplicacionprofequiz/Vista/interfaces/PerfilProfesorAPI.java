package com.valerio.android.aplicacionprofequiz.Vista.interfaces;

import com.valerio.android.aplicacionprofequiz.Vista.models.PerfilProfesor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PerfilProfesorAPI {
    @GET("Angie/Obtener_perfil.php/{cod_prof}")
    public Call<PerfilProfesor>find(@Path("cod_prof")String cod_prof);
}
