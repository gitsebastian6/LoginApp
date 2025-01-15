package com.innovationhtb.loginapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // La URL base de tu API
    private const val BASE_URL = "http://192.168.1.7:8001/"  // Cambia la IP si es necesario



    // Instancia de Retrofit
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Aquí va la URL de tu backend
            .addConverterFactory(GsonConverterFactory.create()) // Para convertir la respuesta JSON
            .build()
    }

    val apiService: ApiService by lazy {
        retrofitInstance.create(ApiService::class.java)
    }
}
