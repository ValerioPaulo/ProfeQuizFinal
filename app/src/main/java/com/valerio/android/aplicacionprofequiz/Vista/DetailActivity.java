package com.valerio.android.aplicacionprofequiz.Vista;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Top;

public class DetailActivity extends AppCompatActivity {

   /* protected void onCreate(Bundle savedInstanceState) {


        Top top = (Top) getIntent().getSerializableExtra("top"); // Obtener el objeto Top del intent

        TextView nombreCompleto = findViewById(R.id.NombreDato);
        TextView nombreCompleto1 = findViewById(R.id.NombreCompletoDato);
        TextView correoProf = findViewById(R.id.CorreoDato);
        ImageView fotoProf = findViewById(R.id.fot);
        TextView carreraProf = findViewById(R.id.CarreraDato);
        TextView calificacion = findViewById(R.id.cal);

        // Asignar valores a los elementos de la vista

        nombreCompleto.setText(top.getNombre_completo());
        nombreCompleto1.setText(top.getNombre_completo());
        correoProf.setText(top.getCorreo_prof());
        Glide.with(this).load(top.getFot_prof()).into(fotoProf);
        carreraProf.setText(top.getCarrera_prof());
        calificacion.setText(top.getCalificacion());
    }
    */
}
