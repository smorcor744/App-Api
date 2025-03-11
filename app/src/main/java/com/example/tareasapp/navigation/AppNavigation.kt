package com.example.tareasapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tareasapp.ViewModel
import com.example.tareasapp.screens.LoginScreen
import com.example.tareasapp.screens.MainMenu
import com.example.tareasapp.screens.PortadaScreen
import com.example.tareasapp.screens.RegisterScreen

@Composable
fun AppNavigation(viewModel: ViewModel, modifier: Modifier) {
    // Se crea y recuerda el controlador de navegación
    val navController = rememberNavController()

    // Definimos el NavHost, que se encarga de gestionar la navegación entre las pantallas
    NavHost(navController = navController, startDestination = "portada") {

        // Ruta para la pantalla de la portada
        composable("portada") {
            // Se llama a la pantalla PortadaScreen, pasando el navController para la navegación
            PortadaScreen(navController)
        }

        // Ruta para la pantalla de inicio de sesión (Login)
        composable("login") {
            // Se llama a la pantalla LoginScreen, pasando el navController y el ViewModel
            LoginScreen(navController, viewModel)
        }

        // Ruta para la pantalla de registro
        composable("register") {
            // Se llama a la pantalla RegisterScreen, pasando el navController y el ViewModel
            RegisterScreen(navController, viewModel)
        }

        // Ruta para la pantalla del menú principal
        composable("menu") {
            // Se llama a la pantalla MainMenu, pasando el navController y el modifier
            MainMenu(navController, modifier,viewModel)
        }
    }
}
