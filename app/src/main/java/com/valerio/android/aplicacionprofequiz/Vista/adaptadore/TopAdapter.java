package com.valerio.android.aplicacionprofequiz.Vista.adaptadore;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.DetailFragment;
import com.valerio.android.aplicacionprofequiz.Vista.SharedViewModel;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Top;

import java.util.ArrayList;
import java.util.List;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> implements Filterable {
    private List<Top> top;
    private List<Top> topFiltered;
    private Context context;
    private SharedViewModel viewModel;

    // Constructor único que acepta todos los parámetros necesarios
    public TopAdapter(List<Top> top, Context context, SharedViewModel viewModel) {
        this.top = top;
        this.topFiltered = new ArrayList<>(top);
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Top item = topFiltered.get(position);
        holder.cod.setText(item.getCod_prof());
        holder.nom.setText(item.getNombre_completo());
        holder.correo.setText(item.getCorreo_prof());
        holder.calificacion.setText(item.getCalificacion());

        holder.itemView.setOnClickListener(v -> {
            if (viewModel != null) {
                viewModel.selectName(item.getNombre_completo());
                // Navega al fragmento de detalle
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.uno, new DetailFragment())
                        .addToBackStack(null)
                        .commit();
            } else {
                Log.e("TopAdapter", "SharedViewModel is null");
            }
        });
    }

    @Override
    public int getItemCount() {
        return topFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Top> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(top);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Top item : top) {
                        if (item.getNombre_completo().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                topFiltered.clear();
                if (results.values != null) {
                    topFiltered.addAll((List<Top>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cod;
        private TextView nom;
        private TextView correo;
        private TextView calificacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cod = itemView.findViewById(R.id.pcod);
            nom = itemView.findViewById(R.id.pnomape);
            correo = itemView.findViewById(R.id.pcorreo);
            calificacion = itemView.findViewById(R.id.pcalificacion);
        }
    }
}

