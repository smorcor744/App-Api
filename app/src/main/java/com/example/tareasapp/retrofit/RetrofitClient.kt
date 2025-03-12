package com.example.tareasapp.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto para gestionar la configuración y creación de la instancia Retrofit.
 * Este cliente permite realizar solicitudes HTTP a la API REST de la aplicación.
 */
object RetrofitClient {
    // URL base de la API
    private const val BASE_URL = "https://api-rest-5rqq.onrender.com/"

    // Token para autenticación del usuario
    private var token: String? = null

    /**
     * Establece un nuevo token de autenticación.
     * @param newToken El token que será utilizado en las solicitudes.
     */
    fun setToken(newToken: String) {
        token = newToken
    }

    /**
     * Interceptor para añadir encabezados de autenticación y formato a cada solicitud HTTP.
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Accept", "application/json") // Cabecera para aceptar respuestas en formato JSON
            .header("Content-Type", "application/json") // Cabecera que indica el tipo de contenido

        token?.let {
            // Si existe un token, se añade como encabezado de autorización
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    /**
     * Cliente HTTP configurado con los interceptores necesarios.
     * - authInterceptor: Añade encabezados personalizados.
     * - HttpLoggingInterceptor: Proporciona logs detallados para debugging.
     */
    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Nivel de logging: muestra cuerpo de solicitudes y respuestas
        })
        .build()

    /**
     * Instancia de Retrofit inicializada de forma lazy.
     * Permite acceder a la API con las configuraciones previamente definidas.
     */
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Define la URL base de la API
            .addConverterFactory(GsonConverterFactory.create()) // Convierte respuestas JSON a objetos Kotlin
            .client(client) // Utiliza el cliente configurado
            .build()
            .create(ApiService::class.java) // Crea la implementación de la interfaz ApiService
    }
}
