package com.example.tareasapp.screens

import android.app.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tareasapp.ViewModel
import com.example.tareasapp.models.Tarea
import com.example.tareasapp.models.TareaBody
import com.example.tareasapp.models.TareaDto
import com.example.tareasapp.retrofit.RetrofitClient
import com.example.tareasapp.utils.BottomBar
import com.example.tareasapp.utils.Header
import kotlinx.coroutines.launch

/**
 * Pantalla principal del menú donde se listan las tareas y se permite crear nuevas.
 */
@Composable
fun MainMenu(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: ViewModel
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Cargar tareas al iniciar si el token es válido
    LaunchedEffect(viewModel.token.value) {
        if (viewModel.token.value.isNotBlank()) {
            viewModel.cargarTareas()
        }
    }

    Scaffold(
        topBar = { Header(navController, modifier) },
        bottomBar = { BottomBar(navController, modifier) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir tarea")
            }
        }
    ) { innerPadding ->
        when {
            // Muestra un indicador de carga mientras se cargan las tareas
            viewModel.isLoadingTareas.value -> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            // Muestra un mensaje de error en caso de fallo
            viewModel.errorTareas.value.isNotEmpty() -> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text(text = viewModel.errorTareas.value, color = Color.Red, modifier = Modifier.padding(16.dp))
                }
            }
            // Lista las tareas disponibles
            else -> {
                LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(viewModel.tareas) { tarea ->
                        TareaItem(
                            tarea = tarea,
                            onComplete = {
                                viewModel.completarTarea(TareaDto(tarea.username, tarea.titulo))
                                tarea.estado = if (tarea.estado == "completada") "pendiente" else "completada"
                                viewModel.cargarTareas()
                            },
                            onDelete = { viewModel.eliminarTareaLocal(tarea) }
                        )
                    }
                }
            }
        }
        if (showCreateDialog) CreateTaskDialog({ navController.navigate("menu") }, viewModel)
    }
}

/**
 * Diálogo para la creación de una nueva tarea.
 */
@Composable
fun CreateTaskDialog(onDismiss: () -> Unit, viewModel: ViewModel) {
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
                        val response = RetrofitClient.instance.crearTarea("Bearer ${viewModel.token}", tareaBody)
                        dialogMessage = if (response.isSuccessful) {
                            "Tarea creada con éxito: ${response.body()?.titulo}"
                        } else {
                            "Error: ${response.errorBody()?.string()}"
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

/**
 * Representación visual de una tarea en la lista.
 */
@Composable
fun TareaItem(
    tarea: Tarea,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tarea.titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = tarea.descripcion, style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                IconButton(onClick = onComplete) {
                    Icon(
                        imageVector = if (tarea.estado == "completada") Icons.Default.Check else Icons.Default.Close,
                        contentDescription = "Completar tarea",
                        tint = if (tarea.estado == "completada") Color.Green else Color.Red
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
