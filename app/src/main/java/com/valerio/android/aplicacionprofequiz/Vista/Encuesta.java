package com.valerio.android.aplicacionprofequiz.Vista;


import static android.graphics.Insets.add;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valerio.android.aplicacionprofequiz.R;

import java.util.HashMap;
import java.util.Map;

public class Encuesta extends AppCompatActivity {

    private RadioGroup question1, question2, question3, question4, question5;
    private EditText commentText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subscriptions);

        // Inicializar Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        question5 = findViewById(R.id.question5);
        commentText = findViewById(R.id.commentText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            // Obtener el valor seleccionado en cada RadioGroup
            int answer1 = getSelectedRadioButtonValue(question1);
            int answer2 = getSelectedRadioButtonValue(question2);
            int answer3 = getSelectedRadioButtonValue(question3);
            int answer4 = getSelectedRadioButtonValue(question4);
            int answer5 = getSelectedRadioButtonValue(question5);

            // Obtener el comentario
            String comment = commentText.getText().toString();

            // URL de tu script PHP en 000webhostapp
            String url = "https://profequiz.000webhostapp.com/oscar/llenar_cuestionario.php";

            // Enviar los datos al servidor utilizando Volley
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Manejar la respuesta del servidor (si es necesario)
                            Toast.makeText(getApplicationContext(), "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Manejar el error de la solicitud (si ocurre)
                    Toast.makeText(getApplicationContext(), "Error al enviar datos", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    // Parámetros a enviar al servidor
                    Map<String, String> params = new HashMap<>();
                    params.put("p1", String.valueOf(answer1));
                    params.put("p2", String.valueOf(answer2));
                    params.put("p3", String.valueOf(answer3));
                    params.put("p4", String.valueOf(answer4));
                    params.put("p5", String.valueOf(answer5));
                    params.put("comentario", comment);
                    return params;
                }
            };

            // Agregar la solicitud a la cola de Volley
            queue.add(stringRequest);
        });
    }

    // Método para obtener el valor seleccionado en un RadioGroup
    private int getSelectedRadioButtonValue(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return Integer.parseInt(selectedRadioButton.getText().toString());
        }
        // En caso de que no se haya seleccionado ninguna opción
        return -1;
    }

}