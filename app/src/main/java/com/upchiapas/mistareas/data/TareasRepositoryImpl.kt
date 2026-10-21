package com.upchiapas.mistareas.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TareasRepositoryImpl : TareasRepository {

    private val tareasSemilla = listOf(
        Tarea(id = 1, titulo = "Estudiar Kotlin", completada = false),
        Tarea(id = 2, titulo = "Hacer los mockups", completada = true),
        Tarea(id = 3, titulo = "Practicar Jetpack Compose", completada = false),
        Tarea(id = 4, titulo = "Subir el proyecto a GitHub", completada = false)
    )

    private val _tareas = MutableStateFlow(tareasSemilla)
    override val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    override fun agregar(titulo: String) {
        val nuevoId = (_tareas.value.maxOfOrNull { it.id } ?: 0) + 1
        _tareas.update { lista -> lista + Tarea(id = nuevoId, titulo = titulo) }
    }

    override fun cambiarCompletada(id: Int) {
        _tareas.update { lista ->
            lista.map { tarea ->
                if (tarea.id == id) tarea.copy(completada = !tarea.completada) else tarea
            }
        }
    }

    override fun eliminar(id: Int) {
        _tareas.update { lista -> lista.filter { it.id != id } }
    }
}