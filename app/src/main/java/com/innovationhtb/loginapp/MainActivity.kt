package com.innovationhtb.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var usernameimput: EditText
    lateinit var paswordimput: EditText
    lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameimput = findViewById(R.id.Username_import)
        paswordimput = findViewById(R.id.Password_import)
        loginBtn = findViewById(R.id.Login_btn)

        loginBtn.setOnClickListener {
            val username = "admin@test.com"
            val password = "test"

            val loginCredentials = LoginCredentials(username, password)

            RetrofitClient.apiService.loginUser(loginCredentials).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            Log.d("Login Success", "Token: ${loginResponse.token}")

                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.e("Login Error", "Credenciales inválidas o error en la API")
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Login Error", "Fallo la solicitud: ${t.message}")
                    Toast.makeText(this@MainActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}


