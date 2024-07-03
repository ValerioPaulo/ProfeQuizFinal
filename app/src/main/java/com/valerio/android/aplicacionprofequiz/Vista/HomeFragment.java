package com.valerio.android.aplicacionprofequiz.Vista;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.adaptadore.TopAdapter;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Top;
import com.valerio.android.aplicacionprofequiz.Vista.net.ApiClient;
import com.valerio.android.aplicacionprofequiz.Vista.net.ApiTop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private List<Top> top;
    private RecyclerView recyclerView;
    private TopAdapter topAdapter;
    private SharedViewModel sharedViewModel;

    private EditText searchEditText;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No es necesario inicializar sharedViewModel aquí
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializa el SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Configura RecyclerView
        recyclerView = rootView.findViewById(R.id.rv_top);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        searchEditText = rootView.findViewById(R.id.searchEditText);

        // Muestra los datos
        showTop();

        // Actualiza el filtro mientras se escribe
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (topAdapter != null) {
                    topAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return rootView;
    }

    public void showTop() {
        Call<List<Top>> call = ApiClient.getClient().create(ApiTop.class).getTop();
        call.enqueue(new Callback<List<Top>>() {
            @Override
            public void onResponse(Call<List<Top>> call, Response<List<Top>> response) {
                if (response.isSuccessful()) {
                    top = response.body();
                    // Inicializa el adaptador con SharedViewModel
                    topAdapter = new TopAdapter(top, getActivity(), sharedViewModel);
                    recyclerView.setAdapter(topAdapter);
                } else {
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Top>> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }
}