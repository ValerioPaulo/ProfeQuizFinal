package com.valerio.android.aplicacionprofequiz.Vista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Esta interface tiene como papel principal describir
//todas las operaciones que solicitaremos de la API
//como editar, listar, eliminar, agregar
public interface ApiMovie {

    //Debemos especificar el metodo de envio HTTP que permite obtener el JSON
    //en este caso es el metodo de envio GET a la ruta movies/list.php
    @GET("movies/list.php")
    Call<List<Movie>> getMovies();
}
