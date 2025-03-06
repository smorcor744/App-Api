package com.example.tareasapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tareasapp.utils.BottomBar
import com.example.tareasapp.utils.Header
import com.example.tareasapp.utils.MenuOption
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.tareasapp.ViewModel
import com.example.tareasapp.models.TareaBody
import com.example.tareasapp.models.TareaDto
import com.example.tareasapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun MainMenu(navController: NavHostController, modifier: Modifier, viewModel: ViewModel) {
    var showTaskListDialog by remember { mutableStateOf(false) }
    var showUserTaskDialog by remember { mutableStateOf(false) }
    var showCreateTaskDialog by remember { mutableStateOf(false) }
    var showDeleteTaskDialog by remember { mutableStateOf(false) }
    var showCompleteTaskDialog by remember { mutableStateOf(false) }

    val token = viewModel.token.value

    Scaffold(
        topBar = { Header(navController, modifier) },
        bottomBar = { BottomBar(navController, modifier) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { MenuOption(Icons.Filled.Task, "Obtener Tareas") { showTaskListDialog = true } }
            item {
                MenuOption(
                    Icons.Filled.Person,
                    "Obtener Tareas del Usuario"
                ) { showUserTaskDialog = true }
            }
            item {
                MenuOption(Icons.Filled.AddCircle, "Crear Tarea") {
                    showCreateTaskDialog = true
                }
            }
            item {
                MenuOption(
                    Icons.Filled.CheckCircle,
                    "Marcar Tarea Completada"
                ) { showCompleteTaskDialog = true }
            }
            item {
                MenuOption(Icons.Filled.Delete, "Eliminar Tarea") {
                    showDeleteTaskDialog = true
                }
            }

        }
    }

    if (showTaskListDialog) TaskListDialog({ showTaskListDialog = false }, token)
    if (showUserTaskDialog) UserTaskDialog({ showUserTaskDialog = false }, token)
    if (showCreateTaskDialog) CreateTaskDialog({ showCreateTaskDialog = false }, token)
    if (showCompleteTaskDialog) CompleteTaskDialog({ showCompleteTaskDialog = false }, token)
    if (showDeleteTaskDialog) DeleteTaskDialog({ showDeleteTaskDialog = false }, token)

}
@Composable
fun TaskListDialog(onDismiss: () -> Unit, token: String) {
    var dialogMessage by remember { mutableStateOf("") }
    var showResultDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Lista de Tareas") },
        text = { Text("Presiona 'Obtener' para cargar las tareas.") },
        confirmButton = {
            TextButton(onClick = {
                scope.launch {
                    try {
                        val response = RetrofitClient.instance.obtenerTareas("Bearer $token")
                        if (response.isSuccessful) {
                            dialogMessage = "Tareas:\n${response.body()}"
                        } else {
                            dialogMessage = "Error: ${response.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        dialogMessage = "Excepción: ${e.message}"
                    }
                    showResultDialog = true
                }
            }) { Text("Obtener") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cerrar") } }
    )

    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Resultado") },
            text = { Text(dialogMessage) },
            confirmButton = { TextButton(onClick = { showResultDialog = false }) { Text("Aceptar") } }
        )
    }
}
@Composable
fun UserTaskDialog(onDismiss: () -> Unit, token: String) {
    var username by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var showResultDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Obtener Tareas del Usuario") },
        text = {
            Column {
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Nombre del Usuario") })
                Spacer(modifier = Modifier.height(8.dp))
                Text("Aquí se mostrarán las tareas de $username")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                scope.launch {
                    try {
                        val response = RetrofitClient.instance.obtenerTareasUsuario("Bearer $token", username)
                        if (response.isSuccessful) {
                            dialogMessage = "Tareas de $username:\n${response.body()?.joinToString("\n") { it.titulo }}"
                        } else {
                            dialogMessage = "Error: ${response.errorBody()?.string()} No tienes permiso para esto"
                        }
                    } catch (e: Exception) {
                        dialogMessage = "Excepción: ${e.message}"
                    }
                    showResultDialog = true
                }
            }) { Text("Buscar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )

    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Resultado") },
            text = { Text(dialogMessage) },
            confirmButton = { TextButton(onClick = { showResultDialog = false }) { Text("Aceptar") } }
        )
    }
}@Composable
fun CreateTaskDialog(onDismiss: () -> Unit, token: String) {
    var username by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var showResultDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Crear Nueva Tarea") },
        text = {
            Column {
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") })
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val tareaBody = TareaBody(username, title, description)
                scope.launch {
                    try {
                        val response = RetrofitClient.instance.crearTarea("Bearer $token", tareaBody)
                        if (response.isSuccessful) {
                            dialogMessage = "Tarea creada con éxito: ${response.body()?.titulo}"
                        } else {
                            dialogMessage = "Error: ${response.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        dialogMessage = "Excepción: ${e.message}"
                    }
                    showResultDialog = true
                }
            }) { Text("Crear") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )

    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Resultado") },
            text = { Text(dialogMessage) },
            confirmButton = { TextButton(onClick = { showResultDialog = false }) { Text("Aceptar") } }
        )
    }
}
@Composable
fun DeleteTaskDialog(onDismiss: () -> Unit, token: String) {
    var username by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var showResultDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var borrar by remember { mutableStateOf(false) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar Tarea") },
        text = {
            Column {
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") })
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título de la Tarea") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                scope.launch {
                    try {
                        val tareaDto = TareaDto(username, title)
                        val response = RetrofitClient.instance.deleteTarea("Bearer $token", tareaDto)
                        if (response.isSuccessful) {
                            dialogMessage = "Tarea eliminada con éxito"
                        } else {
                            dialogMessage = "Error: ${response.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        dialogMessage = "Excepción: ${e.message}"
                    }
                    showResultDialog = true
                }
            }) { Text("Eliminar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )

    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Resultado") },
            text = { Text(dialogMessage) },
            confirmButton = { TextButton(onClick = { showResultDialog = false }) { Text("Aceptar") } }
        )
    }
}

@Composable
fun CompleteTaskDialog(onDismiss: () -> Unit, token: String) {
    var username by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var showResultDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Marcar Tarea como Completada") },
        text = {
            Column {
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") })
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título de la Tarea") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val tareaDto = TareaDto(username, title)
                scope.launch {
                    try {
                        val response = RetrofitClient.instance.completarTarea("Bearer $token", tareaDto)
                        if (response.isSuccessful) {
                            dialogMessage = "Tarea marcada como completada: ${response.body()?.titulo}"
                        } else {
                            dialogMessage = "Error: ${response.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        dialogMessage = "Excepción: ${e.message}"
                    }
                    showResultDialog = true
                }
            }) { Text("Completar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )

    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Resultado") },
            text = { Text(dialogMessage) },
            confirmButton = { TextButton(onClick = { showResultDialog = false }) { Text("Aceptar") } }
        )
    }
}
