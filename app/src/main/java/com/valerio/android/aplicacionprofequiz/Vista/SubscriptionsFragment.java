package com.valerio.android.aplicacionprofequiz.Vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.models.UserSession;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionsFragment extends Fragment {
    //Definicion de variables
    private RadioGroup question1, question2, question3, question4, question5;
    private EditText commentText;
    private Button submitButton;
    private Spinner spinnerOptions;
    private TextView mostrarProfesor;

    private ArrayAdapter<String> spinnerAdapter;
    private String[] professorsArray;

    private String correo = UserSession.getUserEmail();
    private int codigoAlumno;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);

        question1 = view.findViewById(R.id.question1);
        question2 = view.findViewById(R.id.question2);
        question3 = view.findViewById(R.id.question3);
        question4 = view.findViewById(R.id.question4);
        question5 = view.findViewById(R.id.question5);
        commentText = view.findViewById(R.id.commentText);
        submitButton = view.findViewById(R.id.submitButton);
        spinnerOptions = view.findViewById(R.id.spinnerProfessors);
        mostrarProfesor = view.findViewById(R.id.mostrarProfesor);

        // Configurar adaptador para el Spinner
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(spinnerAdapter);

        // Cargar nombres de profesores desde la API
        loadProfessors();

        // Llamar a buscarCodigoAlumno después de cargar profesores
        buscarCodigoAlumno();

        submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                sendPostRequest();
            }
        });

        return view;
    }

    private boolean validateInputs() {
        if (question1.getCheckedRadioButtonId() == -1 ||
                question2.getCheckedRadioButtonId() == -1 ||
                question3.getCheckedRadioButtonId() == -1 ||
                question4.getCheckedRadioButtonId() == -1 ||
                question5.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Por favor, llena toda la encuesta", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (commentText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Por favor, ingresa un comentario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerOptions.getSelectedItem() == null) {
            Toast.makeText(getContext(), "Por favor, selecciona un profesor", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendPostRequest() {
        String url = "https://profequiz.000webhostapp.com/oscar/llenar_cuestionario.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(getContext(), "Envío correcto", Toast.LENGTH_SHORT).show();
                    resetFields();
                },
                error -> Toast.makeText(getContext(), "Envío incorrecto", Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("p1", ((RadioButton) getView().findViewById(question1.getCheckedRadioButtonId())).getText().toString());
                params.put("p2", ((RadioButton) getView().findViewById(question2.getCheckedRadioButtonId())).getText().toString());
                params.put("p3", ((RadioButton) getView().findViewById(question3.getCheckedRadioButtonId())).getText().toString());
                params.put("p4", ((RadioButton) getView().findViewById(question4.getCheckedRadioButtonId())).getText().toString());
                params.put("p5", ((RadioButton) getView().findViewById(question5.getCheckedRadioButtonId())).getText().toString());
                params.put("comentario", commentText.getText().toString());

                // Extraer el código del profesor seleccionado del Spinner
                String selectedProfessor = spinnerOptions.getSelectedItem().toString();
                String codigo = selectedProfessor.split(" - ")[1]; // Asumiendo formato "nombre - codigo"
                params.put("cod_profe", codigo);

                params.put("cod_user", String.valueOf(codigoAlumno));

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void loadProfessors() {
        String url = "https://profequiz.000webhostapp.com/oscar/list-name-cod-profe.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    professorsArray = new String[response.length()];

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            String nombre = response.getJSONObject(i).getString("nombre");
                            String codigo = response.getJSONObject(i).getString("codigo");
                            professorsArray[i] = nombre + " - " + codigo;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    spinnerAdapter.addAll(professorsArray);
                    spinnerAdapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(getContext(), "Error al cargar profesores", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonArrayRequest);
    }

    private void buscarCodigoAlumno() {
        String url = "https://profequiz.000webhostapp.com/oscar/buscar_alumno.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("cod_user")) {
                            codigoAlumno = jsonObject.getInt("cod_user");
                            // Aquí puedes usar el código del alumno (codigoAlumno) según tus necesidades
                            Toast.makeText(getContext(), "Código del alumno obtenido: " + codigoAlumno, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "No se encontró código de alumno válido", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error al obtener código del alumno", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error en la solicitud para obtener código del alumno", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("correo", correo); // Envía el correo del usuario para buscar su código de alumno
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }



    private void resetFields() {
        question1.clearCheck();
        question2.clearCheck();
        question3.clearCheck();
        question4.clearCheck();
        question5.clearCheck();
        commentText.setText("");
        spinnerOptions.setSelection(0);
    }
}