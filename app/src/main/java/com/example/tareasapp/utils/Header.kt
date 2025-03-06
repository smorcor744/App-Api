package com.example.tareasapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tareasapp.ui.theme.Naranja

@Composable
fun Header(navController: NavHostController, modifier: Modifier) {
    // Barra superior con logo, menú y opciones de navegación
    Row(
        modifier = modifier
            .background(color = Naranja) // Fondo naranja para la barra superior
            .fillMaxWidth(), // La fila ocupa todo el ancho disponible
        verticalAlignment = Alignment.CenterVertically // Alineación vertical al centro
    ) {
        // Logo de la aplicación
        /*Image(
            painter = painterResource(id = R.drawable.img), // Carga la imagen del logo
            contentDescription = "Logo", // Descripción del logo para accesibilidad
            modifier = Modifier
                .height(80.dp) // Establece la altura del logo
                .wrapContentHeight() // El logo se ajusta a su altura natural
        )*/

        // Barra de íconos a la derecha con varias opciones
        Row(
            modifier = Modifier
                .fillMaxWidth(), // Ocupa todo el ancho disponible
            horizontalArrangement = Arrangement.End // Alinea los íconos al final (derecha)
        ) {
            // Íconos para diferentes secciones de la aplicación, cada uno es clickeable

            // Ícono de favoritos
            ClickableIcon(nombre = "Favoritos", imageVector = Icons.Filled.Star) {
                navController.navigate("login") // Navega a la pantalla de login al hacer clic
            }

            // Ícono de calendario
            ClickableIcon(nombre = "Calendario", imageVector = Icons.Filled.DateRange) {
                navController.navigate("login") // Navega a la pantalla de login al hacer clic
            }

            // Ícono para añadir entrenamientos
            ClickableIcon(nombre = "Añadir Entrenamientos", imageVector = Icons.Filled.AddCircle) {
                navController.navigate("login") // Navega a la pantalla de login al hacer clic
            }

            // Ícono de sesiones
            ClickableIcon(nombre = "Sessions", imageVector = Icons.Filled.AccountBox) {
                navController.navigate("login") // Navega a la pantalla de login al hacer clic
            }

            // Ícono de menú
            ClickableIcon(nombre = "Menu", imageVector = Icons.Filled.Menu) {
                navController.navigate("login") // Navega a la pantalla de login al hacer clic
            }
        }
    }
}
