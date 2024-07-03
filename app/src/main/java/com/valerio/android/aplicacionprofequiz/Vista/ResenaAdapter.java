package com.valerio.android.aplicacionprofequiz.Vista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valerio.android.aplicacionprofequiz.R;

import java.util.List;

public class ResenaAdapter extends RecyclerView.Adapter<ResenaAdapter.ResenaViewHolder>  {

    public List<Resena> resenaList;
    public ResenaAdapter(List<Resena> resenaList) {
        this.resenaList = resenaList;
    }

    @NonNull
    @Override
    public ResenaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resena, parent, false);
        return new ResenaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResenaViewHolder holder, int position) {
        Resena resena = resenaList.get(position);
        //holder.textViewCodQuiz.setText("Cod Quiz: " + resena.getCodQuiz());
        holder.textViewCodUser.setText("Cod User: " + resena.getCodUser());
        //holder.textViewCodProf.setText("Cod Prof: " + resena.getCodProf());
        //holder.textViewCalificacion.setText("Calificación: " + resena.getCalificacion());
        holder.textViewComentario.setText("Comentario: " + resena.getComentario());

    }

    @Override
    public int getItemCount() {
        return resenaList.size();
    }

    //
    public class ResenaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCodQuiz, textViewCodUser, textViewCodProf, textViewCalificacion, textViewComentario;

        public ResenaViewHolder(@NonNull View itemView) {
            super(itemView);
            //textViewCodQuiz = itemView.findViewById(R.id.textViewCodQuiz);
            textViewCodUser = itemView.findViewById(R.id.textViewCodUser);
            //textViewCodProf = itemView.findViewById(R.id.textViewCodProf);
            //textViewCalificacion = itemView.findViewById(R.id.textViewCalificacion);
            textViewComentario = itemView.findViewById(R.id.textViewComentario);
        }

    }


}
