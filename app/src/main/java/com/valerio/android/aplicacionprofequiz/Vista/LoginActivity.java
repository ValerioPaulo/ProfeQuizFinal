package com.valerio.android.aplicacionprofequiz.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.models.UserSession;
import com.valerio.android.aplicacionprofequiz.databinding.ActivityLoginBinding;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail, editPassword;
    Button btnLogin;
    ActivityLoginBinding mActivityLoginBinding;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);
        setContentView(mActivityLoginBinding.getRoot());

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.iniciarSesion_login_btn);
        executorService = Executors.newSingleThreadExecutor();

        btnLogin.setOnClickListener(v -> {
            String user = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            LoginUsuario(user, password);
        });

        //mActivityLoginBinding.iniciarSesionLoginBtn.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View v) {
        //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
        //}
        //});

        // Ir al registro
        TextView textViewRegistro = findViewById(R.id.textView_registro);
        textViewRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LoginUsuario(String email, String password) {
        executorService.execute(() -> {
            String respuesta;
            try {
                URL url = new URL("https://profequiz.000webhostapp.com/login/index.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "&email=" + email + "&contrasena=" + password;
                byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

                OutputStream os = conn.getOutputStream();
                os.write(postDataBytes);
                os.flush();
                os.close();

                // Leer la respuesta
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                is.close();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    respuesta = sb.toString();
                } else {
                    respuesta = "Error al iniciar sesión";
                }

                conn.disconnect();
            } catch (Exception e) {
                Log.e("Registrar", "Error:" + e.getMessage());
                respuesta = "Error al iniciar sesión";
            }

            final String finalRespuesta = respuesta;

            runOnUiThread(() -> {
                try {
                    JSONObject jsonResponse = new JSONObject(finalRespuesta);

                    String mensaje = jsonResponse.getString("mensaje");
                    //Personalizando el TOAST
                    LayoutInflater inflater = getLayoutInflater();
                    View toastview = inflater.inflate(R.layout.customtoast_layout, (ViewGroup)findViewById(R.id.toast_layout));
                    ImageView logo = toastview.findViewById(R.id.logo);
                    logo.setImageResource(R.drawable.pollocine);
                    TextView message = toastview.findViewById(R.id.tv_message);
                    message.setText(mensaje);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(toastview);
                    toast.show();


                    //Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show();
                    boolean success = mensaje.contains("satisfactorio");
                    if (success) {
                        UserSession.setUserEmail(email);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    //Personalizando el TOAST
                    LayoutInflater inflater = getLayoutInflater();
                    View toastview = inflater.inflate(R.layout.customtoast_layout, (ViewGroup)findViewById(R.id.toast_layout));
                    ImageView logo = toastview.findViewById(R.id.logo);
                    logo.setImageResource(R.drawable.pollocine);
                    TextView message = toastview.findViewById(R.id.tv_message);
                    message.setText("Ingresa sus datos");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(toastview);
                    toast.show();

                    //Toast.makeText(LoginActivity.this, finalRespuesta, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}