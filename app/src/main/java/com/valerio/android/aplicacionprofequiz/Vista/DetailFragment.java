package com.valerio.android.aplicacionprofequiz.Vista;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.adaptadore.PerfilAdapter;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Perfil;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Top;
import com.valerio.android.aplicacionprofequiz.Vista.net.ApiClientPerfil;
import com.valerio.android.aplicacionprofequiz.Vista.net.ApiPerfil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {

    private List<Perfil> perfil;
    private RecyclerView recyclerView;
    private PerfilAdapter perfilAdapter;
    private SharedViewModel sharedViewModel;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // Observe changes in the selected name
        sharedViewModel.getSelectedName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String nombreCompleto) {
                if (nombreCompleto != null) {
                    showPerfil(nombreCompleto);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.cardperfil, container, false);


        recyclerView = rootView.findViewById(R.id.rv_perfil);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        return rootView;
    }



    private void showPerfil(String nombreCompleto) {
        Call<List<Perfil>> call = ApiClientPerfil.getPerfil().create(ApiPerfil.class).getPerfils();
        call.enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (response.isSuccessful()) {
                    perfil = response.body();
                    List<Perfil> filteredList = new ArrayList<>();
                    for (Perfil p : perfil) {
                        if (p.getNombre_completo().equalsIgnoreCase(nombreCompleto)) {
                            filteredList.add(p);
                        }
                    }
                    perfilAdapter = new PerfilAdapter(filteredList, getActivity());
                    recyclerView.setAdapter(perfilAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                // Handle the failure
            }
        });
    }
    }

   /* private static final String ARG_TOP = "top";

    // Método para crear una nueva instancia del fragmento con un objeto Top como argumento
    public static DetailFragment newInstance(Top top) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TOP, top);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_perfil_docente, container, false);

        // Obtener el objeto Top del argumento del fragmento
        Bundle args = getArguments();
        if (args != null) {
            Top top = (Top) args.getSerializable(ARG_TOP);

            // Asignar los valores del objeto Top a los elementos de la vista
            TextView nombreCompleto = view.findViewById(R.id.NombreDato);
            TextView nombreCompleto1 = view.findViewById(R.id.NombreCompletoDato);
            TextView correoProf = view.findViewById(R.id.CorreoDato);
            ImageView fotoProf = view.findViewById(R.id.fot);
            TextView carreraProf = view.findViewById(R.id.CarreraDato);
            TextView calificacion = view.findViewById(R.id.cal);

            nombreCompleto.setText(top.getNombre_completo());
            nombreCompleto1.setText(top.getNombre_completo());
            correoProf.setText(top.getCorreo_prof());
            Glide.with(requireContext()).load(top.getFot_prof()).into(fotoProf);
            carreraProf.setText(top.getCarrera_prof());
            calificacion.setText(top.getCalificacion());
        }

        return view;
    }
 //nuevo comentario  para añadir
    */

