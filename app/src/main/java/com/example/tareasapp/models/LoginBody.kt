package com.example.tareasapp.models

import kotlin.String

data class LoginBody(
    val username: String,
    val password: String
)
data class LoginToken(
    val token: String
)
data class RegisterBody(
    val username: String,
    val email: String,
    val password: String,
    val passwordRepeat: String,
    val rol: String? = "USER",
    val direccion: Direccion
)

data class UsuarioDTO(
    val username: String,
    val email: String,
    val rol: String?
)

data class Tarea(
    val id : String,
    val username: String ,
    val titulo: String,
    val descripcion: String,
    var estado: String? ,
    val fechaCreacion: String?
)

data class TareaBody(
    val username: String ,
    val titulo: String,
    val descripcion: String
)

data class TareaDto(
    val username: String ,
    val titulo: String
)

data class Direccion(
    val calle: String? ="Callejon" ,
    val num: String? = "3",
    val municipio: String? = "Dos Hermanas",
    val provincia: String? = "Sevilla",
    val cp: String? = "12345"
)


