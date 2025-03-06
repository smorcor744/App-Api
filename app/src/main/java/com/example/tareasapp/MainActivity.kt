package com.example.tareasapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tareasapp.navigation.AppNavigation
import com.example.tareasapp.ui.theme.AppEntrenamientoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEntrenamientoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerpading ->
                    val viewModel = ViewModel()
                    AppNavigation(viewModel, Modifier.padding(innerpading))
                }


            }
        }
    }
}
