package com.upchiapas.mistareas.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TareasRepository {

    // Tareas de ejemplo que se cargan al abrir la app
    private val tareasSemilla = listOf(
        Tarea(id = 1, titulo = "Estudiar Kotlin", completada = false),
        Tarea(id = 2, titulo = "Hacer los mockups", completada = true),
        Tarea(id = 3, titulo = "Practicar Jetpack Compose", completada = false),
        Tarea(id = 4, titulo = "Subir el proyecto a GitHub", completada = false)
    )

    // Versión privada que solo el Repository puede modificar
    private val _tareas = MutableStateFlow(tareasSemilla)

    // Versión pública que solo se puede leer
    val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()
}