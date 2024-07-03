package com.valerio.android.aplicacionprofequiz.Vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.valerio.android.aplicacionprofequiz.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResenaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResenaAdapter resenaAdapter;
    private List<Resena> resenaList;
    private ExecutorService executorService;
    private Handler mainHandler;
    private TextView mTvNombreDocente;
    private TextView mTvFechaDocente;
    private ImageView mIvPortada;
    private TextView mTvViewAboutDocente;

    public static String VALOR_DOCENTE = "VALOR_DOCENTE";
    private String nombreDocente;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resena);
        mTvNombreDocente = findViewById(R.id.nombre_docente);
        mTvFechaDocente = findViewById(R.id.fecha_docente);
        mIvPortada = findViewById(R.id.tv_portada);
        mTvViewAboutDocente= findViewById(R.id.about_docente);

        //nombreDocente = getIntent().getStringExtra(VALOR_DOCENTE);
        //mTvNombreDocente.setText(nombreDocente);


        Intent intent = getIntent();
        String nombreDocente = intent.getStringExtra("NOMBRE_DOCENTE");
        String fechaDocente = intent.getStringExtra("FECHA_DOCENTE");
        String urlPortada = intent.getStringExtra("URL_PORTADA");
        String aboutDocente = intent.getStringExtra("ABOUT_DOCENTE");


        mTvNombreDocente.setText(nombreDocente);
        mTvFechaDocente.setText(fechaDocente);
        Glide.with(this).load(urlPortada).into(mIvPortada);

        mTvViewAboutDocente.setText(aboutDocente);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resenaList = new ArrayList<>();
        resenaAdapter = new ResenaAdapter(resenaList);
        recyclerView.setAdapter(resenaAdapter);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        // Ejecutar la tarea para obtener los datos
        fetchData();
    }

    private void fetchData() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Resena> resenaList = new ArrayList<>();
                try {
                    URL url = new URL("https://profequiz.000webhostapp.com/Paulo/Obtener_resena.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    int estado = jsonResponse.getInt("estado");

                    if (estado == 1) {
                        JSONArray resenasArray = jsonResponse.getJSONArray("resenas");
                        for (int i = 0; i < resenasArray.length(); i++) {
                            JSONObject resenaObject = resenasArray.getJSONObject(i);

                            Resena resena = new Resena();
                            resena.setCodQuiz(resenaObject.getInt("cod_quiz"));
                            resena.setCodUser(resenaObject.getInt("cod_user"));
                            resena.setCodProf(resenaObject.getString("cod_prof"));
                            resena.setCalificacion(resenaObject.getString("calificacion"));
                            resena.setComentario(resenaObject.getString("comentario"));

                            resenaList.add(resena);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        resenaAdapter.resenaList.addAll(resenaList);
                        resenaAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /*
    public static Intent newIntent(Context context, String data){
        Intent i = new Intent(context, ResenaActivity.class);
        i.putExtra(VALOR_DOCENTE, data);
        return i;
    }
    */


}