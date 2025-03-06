package com.example.tareasapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.tareasapp.ui.theme.Naranja

@Composable
fun BottomBar(navController: NavHostController, modifier: Modifier) {
    // Row que organiza los iconos en una fila horizontal
    Row(
        modifier = modifier
            .background(color = Naranja) // Aplica el color de fondo Naranja
            .fillMaxWidth(), // Ocupa todo el ancho disponible
        verticalAlignment = Alignment.CenterVertically, // Alinea los iconos verticalmente en el centro
        horizontalArrangement = Arrangement.SpaceBetween // Espacia los iconos equitativamente
    ) {
        // Icono para retroceder a la pantalla anterior
        ClickableIcon(nombre = "Back", imageVector = Icons.Filled.ArrowBack) {
            navController.popBackStack() // Navega a la pantalla anterior
        }

        // Icono para ir al menú principal
        ClickableIcon(nombre = "Home", imageVector = Icons.Filled.Home) {
            navController.navigate("menu") // Navega a la pantalla de menú
        }

        // Icono para acceder a la configuración
        ClickableIcon(nombre = "Settings", imageVector = Icons.Filled.Settings) {
            navController.navigate("settings") // Navega a la pantalla de configuración
        }
    }
}
