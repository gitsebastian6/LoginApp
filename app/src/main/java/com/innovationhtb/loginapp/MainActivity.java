package com.innovationhtb.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.innovationhtb.products.ProductActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.Login_btn);

        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        startActivity(intent);
        finish();
        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor ingresa email y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginCredentials loginCredentials = new LoginCredentials(email, password);

            RetrofitClient.getApiService().loginUser(loginCredentials).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String bearerToken = response.body(); // El token devuelto por la API
                        if (bearerToken != null && !bearerToken.isEmpty()) {
                            Log.d("Login Success", "Token: " + bearerToken);
                            Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();


                            // Navegar a la actividad de productos
                            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("Login Error", "El token está vacío");
                            Toast.makeText(MainActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("Login Error", "Credenciales inválidas o error en la API");
                        Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("Login Error", "Fallo la solicitud: " + t.getMessage(), t);
                    Toast.makeText(MainActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}