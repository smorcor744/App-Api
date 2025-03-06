package com.example.tareasapp.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.tareasapp.ui.theme.White

@Composable
fun ClickableIcon(nombre: String, imageVector: ImageVector, onClick: () -> Unit) {
    // Composable que muestra un icono que es clickeable
    Icon(
        imageVector = imageVector, // El vector de imagen que representa el icono
        contentDescription = nombre, // Descripción del icono para accesibilidad
        tint = White, // Color del icono, en este caso blanco
        modifier = Modifier
            .size(48.dp) // Tamaño del icono
            .padding(5.dp) // Espaciado alrededor del icono
            .clickable { onClick() } // Acción que se ejecuta al hacer clic en el icono
    )
}
