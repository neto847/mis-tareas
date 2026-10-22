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
import com.upchiapas.mistareas.data.TareasRepositoryPrefs
import com.upchiapas.mistareas.ui.TareasScreen
import com.upchiapas.mistareas.ui.TareasViewModel
import com.upchiapas.mistareas.ui.TareasViewModelFactory
import com.upchiapas.mistareas.ui.theme.MisTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val prefs = getSharedPreferences("mis_tareas", MODE_PRIVATE)
        val repository = TareasRepositoryPrefs(prefs)
        setContent {
            MisTareasTheme {
                val viewModel: TareasViewModel = viewModel(
                    factory = TareasViewModelFactory(repository)
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