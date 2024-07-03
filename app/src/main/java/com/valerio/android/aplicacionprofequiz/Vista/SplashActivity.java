package com.valerio.android.aplicacionprofequiz.Vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewStub;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.valerio.android.aplicacionprofequiz.R;

public class SplashActivity extends AppCompatActivity {
    private ViewStub videoStub;
    private VideoView videoView;
    private Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // Configura el video
        videoView = findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.splash_logo; // Reemplaza "splash_logo" por el nombre de tu archivo de video
        videoView.setVideoURI(Uri.parse(path));
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(false);
            mp.start();
            new Handler().postDelayed(() -> {
                // Carga el siguiente activity
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                // Finaliza esta actividad para que no se pueda volver atrás
                finish();
            }, 5500); //   5 segundos
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }


}