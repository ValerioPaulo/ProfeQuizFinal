package com.valerio.android.aplicacionprofequiz.Vista.adaptadore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Perfil;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder> {

    private List<Perfil> perfil;
    private Context context;

    public PerfilAdapter(List<Perfil> perfil, Context context) {
        this.perfil = perfil;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_perfil_docente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // holder.cod.setText(perfil.get(position).getCod_prof());
        holder.nom1.setText(perfil.get(position).getNombre_completo());
        holder.nom.setText(perfil.get(position).getNombre_completo());
        holder.correo.setText(perfil.get(position).getCorreo_prof());
        holder.especial.setText(perfil.get(position).getNombre_completo());
        holder.uni.setText(perfil.get(position).getUni_prof());
        holder.about.setText(perfil.get(position).getAbout_prof());
        holder.curso.setText(perfil.get(position).getCurso_prof());
        holder.meto.setText(perfil.get(position).getMeto_prof());
        holder.calificacion.setText(perfil.get(position).getCalificacion());
    }

    @Override
    public int getItemCount() {
        return perfil.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cod;
        private TextView nom;
        private TextView nom1;
        private TextView correo;
        private TextView especial;
        private TextView uni;
        private TextView about;
        private TextView curso;
        private TextView meto;
        private TextView calificacion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            nom1= itemView.findViewById(R.id.NombreDato);
            calificacion = itemView.findViewById(R.id.cal);
            nom = itemView.findViewById(R.id.NombreCompletoDato);
            correo = itemView.findViewById(R.id.CorreoDato);
            especial = itemView.findViewById(R.id.CarreraDato);
            uni = itemView.findViewById(R.id.UniDato);
            about = itemView.findViewById(R.id.AcercaDato);
            curso = itemView.findViewById(R.id.CursoDato);
            meto = itemView.findViewById(R.id.MetoDato);


        }
    }
}


