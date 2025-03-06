package com.example.tareasapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tareasapp.ui.theme.LightBlue
import com.example.tareasapp.ui.theme.White

@Composable
fun MenuOption(icon: ImageVector, text: String, onClick: () -> Unit) {
    // Box que actúa como contenedor para el ícono y el texto
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f) // El contenedor ocupa el 80% del ancho disponible
            .padding(16.dp) // Padding alrededor del contenedor
            .clickable(onClick = onClick) // Hace que el contenedor sea clickeable
            .background( // Fondo con color y forma personalizada
                color = LightBlue, // Color de fondo
                shape = MaterialTheme.shapes.medium // Forma redondeada para el fondo
            )
            .padding(vertical = 16.dp, horizontal = 24.dp), // Padding interno
        contentAlignment = Alignment.Center // Alineación central del contenido dentro del Box
    ) {
        // Fila horizontal que contiene el ícono y el texto
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alineación vertical centrada
            horizontalArrangement = Arrangement.Center // Alineación horizontal centrada
        ) {
            // Ícono a la izquierda
            Icon(
                imageVector = icon, // El ícono que se pasa como parámetro
                contentDescription = text, // Descripción del ícono para accesibilidad
                modifier = Modifier
                    .size(32.dp) // Tamaño del ícono
                    .fillMaxWidth(0.1f), // El ícono ocupa el 10% del ancho disponible
                tint = White // Color del ícono (blanco)
            )

            // Espaciador entre el ícono y el texto
            Spacer(modifier = Modifier.width(8.dp))

            // Texto a la derecha del ícono
            Text(
                text = text, // El texto que se pasa como parámetro
                color = White, // Color del texto (blanco)
                fontSize = 18.sp, // Tamaño de la fuente
                style = MaterialTheme.typography.bodyMedium, // Estilo de texto
                modifier = Modifier.fillMaxWidth(0.9f) // El texto ocupa el 90% del ancho disponible
            )
        }
    }
}
