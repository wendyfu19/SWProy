package com.example.ale.swproy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ale.swproy.adapters.ListaNavesAdapter;
import com.example.ale.swproy.model.Nave;
import com.example.ale.swproy.model.NaveRespuesta;
import com.example.ale.swproy.service.NaveService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ALE on 20/09/2017.
 */

public class NaveActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "STAR WARS ESPECIES";
    private RecyclerView recyclerView;
    private ListaNavesAdapter listaNavAdapter;

    private boolean aptoParaCargar;
    private int offset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dos);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaNavAdapter = new ListaNavesAdapter(this);
        recyclerView.setAdapter(listaNavAdapter);
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
        NaveService service = retrofit.create(NaveService.class);
        Call<NaveRespuesta> naveRespuestaCall = service.obtenerListaNaves(10,offset);
        naveRespuestaCall.enqueue(new Callback<NaveRespuesta>() {
            @Override
            public void onResponse(Call<NaveRespuesta> call, Response<NaveRespuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {

                    NaveRespuesta navRespuesta = response.body();
                    ArrayList<Nave> listaNaves = navRespuesta.getResults();

                    listaNavAdapter.adicionarListaNaves(listaNaves);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NaveRespuesta> call, Throwable t) {
                Log.e(TAG, "onfailure: " + t.getMessage());
            }
        });



    }
}
