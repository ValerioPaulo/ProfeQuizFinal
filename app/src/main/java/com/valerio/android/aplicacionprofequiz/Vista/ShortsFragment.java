package com.valerio.android.aplicacionprofequiz.Vista;

import static com.valerio.android.aplicacionprofequiz.R.id.NombreDato;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.interfaces.PerfilProfesorAPI;
import com.valerio.android.aplicacionprofequiz.Vista.models.PerfilProfesor;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShortsFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {


    TextView NombreDato;
    TextView NombreCompletoDato;
    TextView CorreoDato;
    TextView CarreraDato;
    TextView UniDato;
    TextView AcercaDato;
    TextView CursoDato;
    TextView MetoDato;


    public ShortsFragment() {
        // Constructor público vacío requerido
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_perfil_docente, container, false);


        NombreDato = view.findViewById(R.id.NombreDato);
        NombreCompletoDato = view.findViewById(R.id.NombreCompletoDato);
        CorreoDato = view.findViewById(R.id.CorreoDato);
        CarreraDato = view.findViewById(R.id.CarreraDato);
        UniDato = view.findViewById(R.id.UniDato);
        AcercaDato = view.findViewById(R.id.AcercaDato);
        CursoDato = view.findViewById(R.id.CursoDato);
        MetoDato = view.findViewById(R.id.MetoDato);




        return view;
    }

    public void find(String codigo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://profequiz.000webhostapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PerfilProfesorAPI perfilProfesorAPI = retrofit.create(PerfilProfesorAPI.class);
        Call<PerfilProfesor> call = perfilProfesorAPI.find(codigo);
        call.enqueue(new Callback<PerfilProfesor>() {
            @Override
            public void onResponse(Call<PerfilProfesor> call, retrofit2.Response<PerfilProfesor> response) {
                try {
                    if (response.isSuccessful()) {
                        PerfilProfesor p = response.body();


                        NombreDato.setText(p.getNom_prof());
                        NombreCompletoDato.setText(p.getNom_prof());
                        CorreoDato.setText(p.getCorreo_prof());
                        CarreraDato.setText(p.getCarrera_prof());
                        UniDato.setText(p.getUni_prof());
                        AcercaDato.setText(p.getAbout_prof());
                        CursoDato.setText(p.getCurso_prof());
                        MetoDato.setText(p.getMeto_prof());

                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PerfilProfesor> call, Throwable t) {
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResponse(JSONObject response) {
        // Implementa esta función según sea necesario
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // Implementa esta función según sea necesario
    }
}
