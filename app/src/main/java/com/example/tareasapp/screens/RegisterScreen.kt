package com.example.tareasapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tareasapp.ViewModel
import com.example.tareasapp.models.Direccion
import com.example.tareasapp.models.LoginBody
import com.example.tareasapp.models.RegisterBody
import com.example.tareasapp.retrofit.RetrofitClient
import com.example.tareasapp.ui.theme.LightBlue
import com.example.tareasapp.ui.theme.Naranja
import com.example.tareasapp.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: ViewModel) {
    // Variables para almacenar los valores del formulario
    var email by remember { mutableStateOf("test@example.com") }
    var username by remember { mutableStateOf("testuser") }
    var password by remember { mutableStateOf("password123") }
    var confirmPass by remember { mutableStateOf("password123") }
    var passwordVisible  = viewModel.passwordVisibility.value
    var confirmPasswordVisible  = viewModel.passwordConfirmVisibility.value
    var rol  by remember { mutableStateOf("USER") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Surface que cubre toda la pantalla y aplica el color de fondo
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Naranja // Fondo de color naranja
    ) {
        // Column organiza los elementos de forma vertical
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente
            verticalArrangement = Arrangement.Center // Centra los elementos verticalmente
        ) {
            // Título de la pantalla
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Registrate", // Texto que aparece en el encabezado
                fontSize = 40.sp, // Tamaño de la fuente
                style = MaterialTheme.typography.labelLarge, // Estilo del texto
                color = White // Color blanco para el texto
            )

            // Campo de texto para ingresar el nombre
            TextField(
                value = email,
                onValueChange = { email = it }, // Actualiza el valor del nombre en el ViewModel
                label = { Text("Email") }, // Etiqueta del campo
                modifier = Modifier.fillMaxWidth(0.8f) // Ajusta el ancho del campo
            )
            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre campos

            // Campo de texto para ingresar el nombre de usuario
            TextField(
                value = username,
                onValueChange = { username = it }, // Actualiza el valor del usuario en el ViewModel
                label = { Text("Usuario") }, // Etiqueta del campo
                modifier = Modifier.fillMaxWidth(0.8f) // Ajusta el ancho del campo
            )
            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre campos

            // Campo de texto para ingresar la contraseña
            TextField(
                value = password,
                onValueChange = { password = it }, // Actualiza el valor de la contraseña
                label = { Text("Contraseña") }, // Etiqueta del campo
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Transforma la contraseña para mostrarla u ocultarla
                trailingIcon = {
                    val image = if (passwordVisible) painterResource(id = android.R.drawable.ic_menu_view)
                    else painterResource(id = android.R.drawable.ic_secure)

                    // Icono para alternar la visibilidad de la contraseña
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Image(painter = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f) // Ajusta el ancho del campo
            )
            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre campos

            // Campo de texto para confirmar la contraseña
            TextField(
                value = confirmPass,
                onValueChange = { confirmPass= it }, // Actualiza el valor de la confirmación de contraseña
                label = { Text("Confirmar Contraseña") }, // Etiqueta del campo
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Transforma la confirmación de contraseña
                trailingIcon = {
                    val image = if (confirmPasswordVisible) painterResource(id = android.R.drawable.ic_menu_view)
                    else painterResource(id = android.R.drawable.ic_secure)

                    // Icono para alternar la visibilidad de la confirmación de contraseña
                    IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                        Image(painter = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f) // Ajusta el ancho del campo
            )

            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre campos

            // Campo de texto para ingresar el rol
            TextField(
                value = rol,
                onValueChange = { rol = it }, // Actualiza el valor del nombre en el ViewModel
                label = { Text("Rol") }, // Etiqueta del campo
                modifier = Modifier.fillMaxWidth(0.8f) // Ajusta el ancho del campo
            )
            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre campos

            // Fila con dos botones: Cancelar y Aceptar
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp) // Espaciado superior
            ) {
                // Botón de Cancelar
                Button(
                    modifier = Modifier.weight(1f), // Ocupa el mismo espacio que el siguiente botón
                    colors = ButtonDefaults.buttonColors(LightBlue), // Color de fondo del botón
                    onClick = {
                        navController.navigate("login") // Navega a la pantalla de login
                    }
                ) {
                    Text("Cancelar") // Texto del botón
                }

                Spacer(modifier = Modifier.width(20.dp)) // Espacio entre los botones

                // Botón de Aceptar
                Button(
                    modifier = Modifier.weight(1f), // Ocupa el mismo espacio que el siguiente botón
                    colors = ButtonDefaults.buttonColors(LightBlue), // Color de fondo del botón
                    onClick = {
                        scope.launch {
                            try {
                                val registerBody = RegisterBody(username, email, password, confirmPass, rol,Direccion("Callejon" ,"3","Dos Hermanas","Sevilla","12345"))
                                val response = RetrofitClient.instance.register(registerBody)
                                if (response.isSuccessful) {
                                    dialogMessage = "Registro exitoso. Usuario: ${response.body()?.username}"
                                } else {
                                    val errorBody = response.errorBody()?.string()
                                    dialogMessage = "Error: $errorBody" // Muestra el cuerpo del error
                                    println("Error Body: $errorBody") // Imprime el cuerpo del error
                                }
                            } catch (e: Exception) {
                                dialogMessage = "Error: ${e.message}"
                                println("Error: ${e.message}") // Imprime el error
                            }
                            showDialog = true
                        }
                    }
                ) {
                    Text("Aceptar") // Texto del botón
                }
            }

        }

    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Aceptar")
                }
            },
            title = { Text("Respuesta del Servidor") },
            text = { Text(dialogMessage) }
        )
    }
}
