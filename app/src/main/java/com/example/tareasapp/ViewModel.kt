package com.example.tareasapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareasapp.models.Tarea
import com.example.tareasapp.models.TareaBody
import com.example.tareasapp.models.TareaDto
import com.example.tareasapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar la lógica de autenticación y manejo de tareas de la aplicación.
 */
class ViewModel : ViewModel() {

    // Región: Autenticación
    private val _token = mutableStateOf("") // Token del usuario autenticado
    val token: State<String> = _token

    private val _sessions = mutableStateMapOf<String, String>("Sergio" to "1234") // Mapa de usuarios y contraseñas

    // Campos para formularios de autenticación
    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> = _confirmPassword

    private val _passwordVisibility = mutableStateOf(false) // Estado de visibilidad de contraseña
    val passwordVisibility: State<Boolean> = _passwordVisibility

    private val _passwordConfirmVisibility = mutableStateOf(false) // Estado de visibilidad de confirmación de contraseña
    val passwordConfirmVisibility: State<Boolean> = _passwordConfirmVisibility
    // Fin Región: Autenticación

    // Región: Gestión de Tareas
    private val _tareas = mutableStateListOf<Tarea>() // Lista de tareas
    val tareas: List<Tarea> get() = _tareas

    private val _isLoadingTareas = mutableStateOf(false) // Indicador de carga
    val isLoadingTareas: State<Boolean> = _isLoadingTareas

    private val _errorTareas = mutableStateOf("") // Mensaje de error
    val errorTareas: State<String> = _errorTareas

    /**
     * Actualiza una tarea existente en la lista.
     * @param tarea Tarea a actualizar.
     */
    fun actualizarTarea(tarea: Tarea) {
        val index = _tareas.indexOfFirst { it.id == tarea.id }
        if (index != -1) {
            _tareas[index] = tarea
        }
    }
    // Fin Región: Gestión de Tareas

    // Región: Métodos de Autenticación
    fun changeToken(newToken: String) {
        if (newToken.isNotBlank()) _token.value = "Bearer $newToken"
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmPasswordChanged(newPassword: String) {
        _confirmPassword.value = newPassword
    }

    fun togglePasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value
    }

    fun toggleConfirmPasswordVisibility() {
        _passwordConfirmVisibility.value = !_passwordConfirmVisibility.value
    }

    /**
     * Añade una nueva sesión si las validaciones son correctas.
     * @return Verdadero si se añade exitosamente, falso en caso contrario.
     */
    fun addSession(): Boolean {
        return if (!confirmUsernameError() && confirmPasswordError()) {
            _sessions[username.value] = password.value
            true
        } else false
    }

    fun confirmUsernameError(): Boolean {
        return _sessions.containsKey(username.value)
    }

    fun confirmPasswordError(): Boolean {
        return password.value == confirmPassword.value
    }

    /**
     * Confirma las credenciales del usuario para el inicio de sesión.
     */
    fun confirmLogin(): Boolean {
        return _username.value.isNotEmpty() &&
                _password.value.isNotEmpty() &&
                _sessions.containsKey(username.value) &&
                _sessions[username.value] == password.value
    }
    // Fin Región: Métodos de Autenticación

    // Región: Métodos de Tareas
    /**
     * Carga las tareas desde el servidor.
     */
    fun cargarTareas() {
        viewModelScope.launch {
            _isLoadingTareas.value = true
            try {
                val response = RetrofitClient.instance.obtenerTareas(_token.value)
                if (response.isSuccessful) {
                    _tareas.clear()
                    response.body()?.let { _tareas.addAll(it) }
                    _errorTareas.value = ""
                } else {
                    _errorTareas.value = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorTareas.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoadingTareas.value = false
            }
        }
    }

    /**
     * Crea una nueva tarea enviándola al servidor.
     */
    fun crearTarea(tarea: TareaBody) {
        viewModelScope.launch {
            _isLoadingTareas.value = true
            try {
                val response = RetrofitClient.instance.crearTarea(_token.value, tarea)
                if (response.isSuccessful) {
                    response.body()?.let { nuevaTarea ->
                        _tareas.add(nuevaTarea)
                        _errorTareas.value = ""
                    }
                } else {
                    _errorTareas.value = "Error al crear tarea: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorTareas.value = "Error: ${e.message}"
            } finally {
                _isLoadingTareas.value = false
            }
        }
    }

    /**
     * Marca una tarea como completada en el servidor.
     */
    fun completarTarea(tarea: TareaDto) {
        viewModelScope.launch {
            _isLoadingTareas.value = true
            try {
                val response = RetrofitClient.instance.completarTarea(_token.value, tarea)
                if (response.isSuccessful) {
                    cargarTareas()
                } else {
                    _errorTareas.value = "Error al modificar la tarea: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorTareas.value = "Error: ${e.message}"
            } finally {
                _isLoadingTareas.value = false
            }
        }
    }

    /**
     * Elimina una tarea localmente de la lista.
     */
    fun eliminarTareaLocal(tarea: Tarea) {
        _tareas.remove(tarea)
    }
    // Fin Región: Métodos de Tareas
}
