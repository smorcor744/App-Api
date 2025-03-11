package com.example.tareasapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

class ViewModel {

    private val _token =  mutableStateOf<String>("")
    var token = _token

    fun changeToken(newToken: String?){
        if (!newToken.isNullOrBlank()) _token.value = newToken
    }

    private val _loginState: String? = ""
    var loginState = _loginState


    // Almacena las sesiones que se hayan creado (usuario -> contraseña)
    private val _sessions = mutableStateMapOf<String, String>("Sergio" to "1234")

    // Variables de estado para los campos de usuario, contraseña y confirmación
    private var _username = mutableStateOf<String>("")
    val username: State<String> = _username

    private var _name = mutableStateOf<String>("")
    val name: State<String> = _name

    private var _password = mutableStateOf<String>("")
    val password: State<String> = _password

    private var _confirmPassword = mutableStateOf<String>("")
    val confirmPassword: State<String> = _confirmPassword

    // Estados para controlar la visibilidad de las contraseñas
    private var _passwordVisibility = mutableStateOf<Boolean>(false)
    val passwordVisibility: State<Boolean> = _passwordVisibility

    private var _passwordConfirmVisibility = mutableStateOf<Boolean>(false)
    val passwordConfirmVisibility: State<Boolean> = _passwordConfirmVisibility

    // Función para actualizar el nombre de usuario
    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    // Función para actualizar el nombre de usuario
    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    // Función para actualizar la contraseña
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // Función para actualizar la confirmación de la contraseña
    fun onConfirmPasswordChanged(newPassword: String) {
        _confirmPassword.value = newPassword
    }

    // Alterna la visibilidad de la contraseña
    fun togglePasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value
    }

    // Alterna la visibilidad de la confirmación de la contraseña
    fun toggleConfirmPasswordVisibility() {
        _passwordConfirmVisibility.value = !_passwordConfirmVisibility.value
    }


    // Función para agregar una nueva sesión si las validaciones son correctas
    fun addSession(): Boolean {
        return if (!confirmUsernameError() && confirmPasswordError()) {
            _sessions[username.value] = password.value
            true
        } else false
    }

    // Valida que el nombre de usuario y su confirmación coincidan
    fun confirmUsernameError(): Boolean {
        return _sessions.containsKey(_username.value)
    }

    // Valida que la contraseña y su confirmación coincidan
    fun confirmPasswordError(): Boolean {
        return _password.value == _confirmPassword.value
    }

    // Verifica si el inicio de sesión es válido (usuario y contraseña correctos)
    fun confirmLogin(): Boolean {
        // Comprueba que el nombre de usuario y la contraseña no estén vacíos
        if (_username.value.isNotEmpty() && _password.value.isNotEmpty()) {
            // Verifica si el usuario existe y la contraseña es correcta
            if (_sessions.containsKey(_username.value) && _sessions[_username.value] == _password.value) {
                return true
            }
        }
        return false
    }
}
