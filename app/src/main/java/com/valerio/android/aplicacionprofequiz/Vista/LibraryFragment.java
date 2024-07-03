package com.valerio.android.aplicacionprofequiz.Vista;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.valerio.android.aplicacionprofequiz.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibraryFragment extends Fragment {
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private String nombreDocente;
    private String fechaDocente;
    private String aboutDocente;



    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        showMovies();

        return view;
    }


    // Un método encargado de ejecutar el llamado a la API
    public void showMovies() {
        Call<List<Movie>> call = ApiClient.getClient().create(ApiMovie.class).getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            // Este método nos permitirá recuperar el JSON y pasar a nuestro
            // objeto tipo lista llamado Movie para luego pasárselo a
            // nuestro adaptador y que esto se encargue de visualizar todos
            // estos datos en el RecyclerView
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    movies = response.body();
                    movieAdapter = new MovieAdapter(movies, getContext());
                    movieAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getContext(),"Titulo: " + movies.get(recyclerView.getChildAdapterPosition(v)).getTitulo(), Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(getActivity(), ResenaActivity.class);
                            //startActivity(intent);
                            nombreDocente = movies.get(recyclerView.getChildAdapterPosition(v)).getTitulo();
                            fechaDocente = movies.get(recyclerView.getChildAdapterPosition(v)).getFecha();
                            String urlPortada = movies.get(recyclerView.getChildAdapterPosition(v)).getPortada(); // Obtén la URL de la portada
                            aboutDocente = movies.get(recyclerView.getChildAdapterPosition(v)).getGenero();
                            ////////////////////////////////////////////////
                            //Intent i = ResenaActivity.newIntent(getContext(), nombreDocente );
                            //startActivity(i);

                            Intent intent = new Intent(getActivity(), ResenaActivity.class);
                            intent.putExtra("NOMBRE_DOCENTE", nombreDocente);
                            intent.putExtra("FECHA_DOCENTE", fechaDocente);
                            intent.putExtra("URL_PORTADA", urlPortada);
                            intent.putExtra("ABOUT_DOCENTE", aboutDocente);
                            startActivity(intent);
                        }
                    });

                    recyclerView.setAdapter(movieAdapter);
                }
            }
            // En caso de error de conexión con el servidor
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        });
    }






}