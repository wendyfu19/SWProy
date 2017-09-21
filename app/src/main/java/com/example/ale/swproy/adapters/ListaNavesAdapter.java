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
import com.example.ale.swproy.model.Nave;

import java.util.ArrayList;

/**
 * Created by ALE on 20/09/2017.
 */

public class ListaNavesAdapter extends RecyclerView.Adapter<ListaNavesAdapter.ViewHolder> {
    private ArrayList<Nave> dataset;
    private Context context;

    public ListaNavesAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nave, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Nave p = dataset.get(position);
        holder.nameTextView.setText(p.getName());
        holder.modelTextView.setText(p.getModel());
        holder.lengthTextView.setText(p.getLength());

        Glide.with(context)
                .load("http://www.gifandgif.es/gifs_animados/Star_wars/Gifs%20Animados%20Star%20Wars%20%2812%29.gif")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.LogoView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaNaves(ArrayList<Nave> listaNave) {
        dataset.addAll(listaNave);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView nameTextView;
        private TextView modelTextView;
        private TextView lengthTextView;
        private ImageView LogoView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            modelTextView = (TextView) itemView.findViewById(R.id.modelTextView);
            lengthTextView = (TextView) itemView.findViewById(R.id.lengthTextView);
            LogoView = (ImageView) itemView.findViewById(R.id.LogoView);

        }
    }
}
