package com.example.tareasapp.retrofit


import com.example.tareasapp.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.HTTP


interface ApiService {
    @POST("usuarios/login")
    suspend fun login(@Body request: LoginBody): Response<LoginToken>

    @POST("usuarios/register")
    suspend fun register(@Body request: RegisterBody): Response<UsuarioDTO>

    @GET("tareas")
    suspend fun obtenerTareas(@Header("Authentication") token: String): Response<List<Tarea>>

    @GET("tareas/{username}")
    suspend fun obtenerTareasUsuario(
        @Header("Authentication") token: String,
        @Path("username") username: String
    ): Response<List<Tarea>>

    @POST("tareas")
    suspend fun crearTarea(
        @Header("Authentication") token: String,
        @Body request: TareaBody
    ): Response<Tarea>


    @PUT("tareas/completar")
    suspend fun completarTarea(
        @Header("Authentication") token: String,
        @Body request: TareaDto
    ): Response<Tarea>

    @DELETE("tareas")
    suspend fun deleteTarea(
        @Header("Authorization") token: String,
        @Body request: TareaDto
    ): Response<String>

}
