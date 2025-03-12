package com.example.tareasapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tareasapp.ViewModel
import com.example.tareasapp.models.LoginBody
import com.example.tareasapp.retrofit.RetrofitClient
import com.example.tareasapp.ui.theme.LightBlue
import com.example.tareasapp.ui.theme.Naranja
import com.example.tareasapp.ui.theme.White
import kotlinx.coroutines.launch

/**
 * Pantalla de inicio de sesión donde el usuario introduce sus credenciales.
 * @param navController Controlador de navegación para redirigir al usuario a otras pantallas.
 * @param viewModel ViewModel que gestiona el estado de la pantalla.
 */
@Composable
fun LoginScreen(navController: NavHostController, viewModel: ViewModel) {
    // Declaración de variables de estado para los campos de texto y visibilidad de contraseña.
    var username = viewModel.username.value
    var password = viewModel.password.value
    var passwordVisible = viewModel.passwordVisibility.value
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Naranja
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título principal
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Inicia Sesión",
                fontSize = 40.sp,
                style = MaterialTheme.typography.labelLarge,
                color = White
            )

            // Campo de texto para usuario
            TextField(
                value = username,
                onValueChange = { viewModel.onUsernameChanged(it) },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para contraseña
            TextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        painterResource(id = android.R.drawable.ic_menu_view)
                    else painterResource(id = android.R.drawable.ic_secure)

                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Image(painter = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            // Enlace para recuperar contraseña
            Text(
                text = "¿Has olvidado la contraseña?",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable { navController.navigate("menu") }
                    .padding(top = 10.dp),
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Botones para iniciar sesión o registrar
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(LightBlue),
                    onClick = {
                        scope.launch {
                            try {
                                // Realiza la solicitud de inicio de sesión
                                val response = RetrofitClient.instance.login(LoginBody(username, password))
                                dialogMessage = response.body().toString()
                                if (response.isSuccessful) {
                                    val token = response.body()?.token
                                    if (!token.isNullOrEmpty()) {
                                        viewModel.changeToken(token)
                                        RetrofitClient.setToken(token)
                                        dialogMessage = "Registro exitoso. Token: $token"
                                        navController.navigate("menu")
                                    } else {
                                        dialogMessage = "Error: No se recibió un token válido"
                                    }
                                } else {
                                    val errorBody = response.errorBody()?.string()
                                    dialogMessage = "Error: $errorBody"
                                }
                            } catch (e: Exception) {
                                dialogMessage = "Error: ${e.message}"
                            }
                            showDialog = true
                        }
                    }
                ) { Text("Iniciar sesión") }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(LightBlue),
                    onClick = { navController.navigate("register") }
                ) { Text("Registrar usuario") }
            }
        }
    }

    // Diálogo para mostrar mensajes de respuesta del servidor
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = { TextButton(onClick = { showDialog = false }) { Text("Aceptar") } },
            title = { Text("Respuesta del Servidor") },
            text = { Text(dialogMessage) }
        )
    }
}
