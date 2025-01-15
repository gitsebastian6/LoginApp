package com.innovationhtb.loginapp

data class LoginResponse(
    val token: String, // Aquí pondrás los datos que devuelva tu API, como el token
    val userId: Int    // Este es solo un ejemplo, depende de lo que tu API devuelva
)
