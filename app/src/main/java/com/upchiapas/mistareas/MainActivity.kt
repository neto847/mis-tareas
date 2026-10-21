package com.upchiapas.mistareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.mistareas.data.TareasRepositoryImpl
import com.upchiapas.mistareas.ui.TareasScreen
import com.upchiapas.mistareas.ui.TareasViewModel
import com.upchiapas.mistareas.ui.TareasViewModelFactory
import com.upchiapas.mistareas.ui.theme.MisTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisTareasTheme {
                // Aquí se inyecta el Repository concreto a la Factory
                val viewModel: TareasViewModel = viewModel(
                    factory = TareasViewModelFactory(TareasRepositoryImpl())
                )
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TareasScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}