package com.example.tareasapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tareasapp.ui.theme.LightBlue
import com.example.tareasapp.ui.theme.Naranja
import com.example.tareasapp.ui.theme.White

@Composable
fun PortadaScreen(navController: NavHostController) {
    // Surface es un contenedor que aplica un fondo y estilos a la pantalla
    Surface(
        modifier = Modifier.fillMaxSize().background(Naranja), // Fondo naranja para toda la pantalla
        color = MaterialTheme.colorScheme.background // Color de fondo definido por el tema
    ) {

        // Column organiza los elementos de forma vertical
        Column(
            modifier = Modifier.fillMaxSize().background(Naranja), // Rellenar toda la pantalla con fondo naranja
            horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente
            verticalArrangement = Arrangement.Center // Centra los elementos verticalmente
        ) {
            // Imagen del logo de la app
            /*Image(
                painter = painterResource(id = R.drawable.img), // Carga la imagen desde los recursos
                contentDescription = "Logo", // Descripción para accesibilidad
                modifier = Modifier
                    .height(300.dp) // Establece la altura de la imagen
                    .wrapContentHeight() // Ajusta la altura según el contenido
            )*/
            // Título de la aplicación
            Text(
                text = "Entrenamientos App", // Texto que se muestra
                fontSize = 25.sp , // Tamaño de la fuente
                style = MaterialTheme.typography.labelLarge, // Estilo de texto según el tema
                color = White // Color blanco para el texto
            )
            // Espaciador entre el título y el botón
            Spacer(modifier = Modifier.height(16.dp))
            // Botón de "Entrar" para navegar a la pantalla de login
            Button(
                onClick = { navController.navigate("login") }, // Acción al hacer clic, navega a "login"
                colors = ButtonDefaults.buttonColors(LightBlue) // Color de fondo del botón
            ) {
                Text(
                    "Entrar", // Texto dentro del botón
                    fontSize = 20.sp , // Tamaño de la fuente del texto
                )
            }
        }
    }
}
