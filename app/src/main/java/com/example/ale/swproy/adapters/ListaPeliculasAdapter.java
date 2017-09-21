package com.example.ale.swproy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ale.swproy.R;
import com.example.ale.swproy.model.Pelicula;

import java.util.ArrayList;

/**
 * Created by ALE on 20/09/2017.
 */

public class ListaPeliculasAdapter extends RecyclerView.Adapter<ListaPeliculasAdapter.ViewHolder>{
    private ArrayList<Pelicula> dataset;
    private Context context;

    public ListaPeliculasAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }
    @Override
    public ListaPeliculasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelicula, parent, false);
        return new ListaPeliculasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListaPeliculasAdapter.ViewHolder holder, int position) {
        Pelicula p = dataset.get(position);
        holder.titleTextView.setText(p.getTitle());
        Glide.with(context)
                .load("http://pixel.nymag.com/imgs/daily/vulture/2015/10/19/starwars.w529.h352.gif")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.LogoView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPelicula(ArrayList<Pelicula> listaPersona) {
        dataset.addAll(listaPersona);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //private TextView episodeTextView;
        private TextView titleTextView;
        private ImageView LogoView;
        public ViewHolder(View itemView) {
            super(itemView);
            //episodeTextView = (TextView) itemView.findViewById(R.id.episodeTextView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            LogoView = (ImageView) itemView.findViewById(R.id.LogoView);

        }
    }
}
