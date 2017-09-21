package com.example.ale.swproy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ale.swproy.adapters.ListaPeliculasAdapter;
import com.example.ale.swproy.model.Pelicula;
import com.example.ale.swproy.model.PeliculaRespuesta;
import com.example.ale.swproy.service.PeliculaService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ALE on 20/09/2017.
 */

public class PeliculaActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "STAR WARS PELICULAS";
    private RecyclerView recyclerView;
    private ListaPeliculasAdapter listaPeliAdapter;

    private boolean aptoParaCargar;
    private int offset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tres);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPeliAdapter = new ListaPeliculasAdapter(this);
        recyclerView.setAdapter(listaPeliAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 10;
                            ObtenerDatos(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset = 0;
        ObtenerDatos(offset);

    }
    private void ObtenerDatos(int offset) {
        PeliculaService service = retrofit.create(PeliculaService.class);
        Call<PeliculaRespuesta> peliRespuestaCall = service.obtenerListaPeliculas(10,offset);
        peliRespuestaCall.enqueue(new Callback<PeliculaRespuesta>() {
            @Override
            public void onResponse(Call<PeliculaRespuesta> call, Response<PeliculaRespuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {

                    PeliculaRespuesta navRespuesta = response.body();
                    ArrayList<Pelicula> listaNaves = navRespuesta.getResults();

                    listaPeliAdapter.adicionarListaPelicula(listaNaves);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PeliculaRespuesta> call, Throwable t) {
                Log.e(TAG, "onfailure: " + t.getMessage());
            }
        });



    }
}
