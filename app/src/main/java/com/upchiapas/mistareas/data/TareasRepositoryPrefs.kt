package com.upchiapas.mistareas.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TareasRepositoryPrefs(
    private val prefs: SharedPreferences
) : TareasRepository {

    private val tareasSemilla = listOf(
        Tarea(id = 1, titulo = "Estudiar Kotlin", completada = false),
        Tarea(id = 2, titulo = "Hacer los mockups", completada = true),
        Tarea(id = 3, titulo = "Practicar Jetpack Compose", completada = false),
        Tarea(id = 4, titulo = "Subir el proyecto a GitHub", completada = false)
    )

    private val _tareas = MutableStateFlow(cargar())
    override val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    override fun agregar(titulo: String) {
        val nuevoId = (_tareas.value.maxOfOrNull { it.id } ?: 0) + 1
        _tareas.update { lista -> lista + Tarea(id = nuevoId, titulo = titulo) }
        guardar()
    }

    override fun cambiarCompletada(id: Int) {
        _tareas.update { lista ->
            lista.map { tarea ->
                if (tarea.id == id) tarea.copy(completada = !tarea.completada) else tarea
            }
        }
        guardar()
    }

    override fun eliminar(id: Int) {
        _tareas.update { lista -> lista.filter { it.id != id } }
        guardar()
    }

    // Lee el texto guardado y lo convierte en lista de tareas
    private fun cargar(): List<Tarea> {
        val texto = prefs.getString(CLAVE_TAREAS, null) ?: return tareasSemilla
        if (texto.isEmpty()) return emptyList()
        return texto.lines().map { linea ->
            val partes = linea.split("|")
            Tarea(
                id = partes[0].toInt(),
                titulo = partes[1],
                completada = partes[2].toBoolean()
            )
        }
    }

    // Convierte la lista en un solo texto: una tarea por línea
    private fun guardar() {
        val texto = _tareas.value.joinToString("\n") { tarea ->
            val titulo = tarea.titulo.replace("|", " ").replace("\n", " ")
            "${tarea.id}|$titulo|${tarea.completada}"
        }
        prefs.edit().putString(CLAVE_TAREAS, texto).apply()
    }

    companion object {
        private const val CLAVE_TAREAS = "tareas"
    }
}