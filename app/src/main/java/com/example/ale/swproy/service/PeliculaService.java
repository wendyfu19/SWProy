package com.example.ale.swproy.service;

import com.example.ale.swproy.model.PeliculaRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ALE on 21/09/2017.
 */

public interface PeliculaService {
    @GET("films")
    Call<PeliculaRespuesta> obtenerListaPeliculas(@Query("limit") int limit, @Query("offset") int offset);
}
