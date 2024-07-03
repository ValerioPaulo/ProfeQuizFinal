package com.valerio.android.aplicacionprofequiz.Vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.valerio.android.aplicacionprofequiz.R;

import java.util.List;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements View.OnClickListener {

    private List<Movie> movies;
    //permitirá acceder al conexto del activity_main que muestra el recyclerview
    private Context context;

    private View.OnClickListener listener;


    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_item,parent, false); ////////////////////////////////////
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_titulo.setText(movies.get(position).getTitulo());
        Glide.with(context).load(movies.get(position).getPortada()).into(holder.iv_portada);
        holder.tv_bed.setText(movies.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_portada;
        private TextView tv_titulo;
        private TextView tv_bed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_portada = itemView.findViewById(R.id.pic);
            tv_titulo = itemView.findViewById(R.id.titleTxt);
            tv_bed = itemView.findViewById(R.id.bedTxt);


        }
    }




}
