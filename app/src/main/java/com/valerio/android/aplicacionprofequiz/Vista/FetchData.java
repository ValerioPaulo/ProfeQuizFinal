package com.valerio.android.aplicacionprofequiz.Vista;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchData  extends AsyncTask<Void, Void, List<Resena>> {


    private static final String URL_STRING = "https://profequiz.000webhostapp.com/Paulo/Obtener_resena.php";


    protected List<Resena> doInBackground(Void... voids) {
        List<Resena> resenaList = new ArrayList<>();
        try {
            URL url = new URL(URL_STRING);
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
            Log.e("FetchData", "Error fetching data", e);
        }
        return resenaList;
    }

    @Override
    protected void onPostExecute(List<Resena> resenaList) {
        super.onPostExecute(resenaList);
        // Aquí puedes actualizar la UI con los datos obtenidos
        for (Resena resena : resenaList) {
            Log.d("Resena", "Quiz: " + resena.getCodQuiz() + ", User: " + resena.getCodUser() + ", Profesor: " + resena.getCodProf() + ", Calificación: " + resena.getCalificacion() + ", Comentario: " + resena.getComentario());
        }
    }




}
