package com.example.tareasapp.retrofit

import com.example.tareasapp.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz que define los métodos para interactuar con la API REST.
 * Cada método corresponde a una solicitud HTTP específica.
 */
interface ApiService {

    /**
     * Inicia sesión del usuario enviando las credenciales y obtiene un token de autenticación.
     * @param request Cuerpo de la solicitud que incluye usuario y contraseña.
     * @return Respuesta que contiene el token de autenticación.
     */
    @POST("usuarios/login")
    suspend fun login(@Body request: LoginBody): Response<LoginToken>

    /**
     * Registra un nuevo usuario con la información proporcionada.
     * @param request Cuerpo de la solicitud que incluye los datos del usuario.
     * @return Respuesta con el usuario recién creado.
     */
    @POST("usuarios/register")
    suspend fun register(@Body request: RegisterBody): Response<UsuarioDTO>

    /**
     * Obtiene todas las tareas disponibles para el usuario autenticado.
     * @param token Token de autenticación incluido como encabezado.
     * @return Lista de tareas.
     */
    @GET("tareas")
    suspend fun obtenerTareas(@Header("Authentication") token: String): Response<List<Tarea>>

    /**
     * Obtiene las tareas específicas de un usuario.
     * @param token Token de autenticación incluido como encabezado.
     * @param username Nombre de usuario del cual se obtendrán las tareas.
     * @return Lista de tareas del usuario.
     */
    @GET("tareas/{username}")
    suspend fun obtenerTareasUsuario(
        @Header("Authentication") token: String,
        @Path("username") username: String
    ): Response<List<Tarea>>

    /**
     * Crea una nueva tarea en el sistema.
     * @param token Token de autenticación incluido como encabezado.
     * @param request Cuerpo de la solicitud con los detalles de la nueva tarea.
     * @return La tarea recién creada.
     */
    @POST("tareas")
    suspend fun crearTarea(
        @Header("Authentication") token: String,
        @Body request: TareaBody
    ): Response<Tarea>

    /**
     * Marca una tarea como completada.
     * @param token Token de autenticación incluido como encabezado.
     * @param request Cuerpo de la solicitud con los detalles de la tarea a completar.
     * @return La tarea modificada como completada.
     */
    @PUT("tareas/completar")
    suspend fun completarTarea(
        @Header("Authentication") token: String,
        @Body request: TareaDto
    ): Response<Tarea>

    /**
     * Elimina una tarea del sistema.
     * @param token Token de autenticación incluido como encabezado.
     * @param request Cuerpo de la solicitud con los detalles de la tarea a eliminar.
     * @return Respuesta indicando el resultado de la operación.
     */
    @DELETE("tareas")
    suspend fun deleteTarea(
        @Header("Authorization") token: String,
        @Body request: TareaDto
    ): Response<String>
}
