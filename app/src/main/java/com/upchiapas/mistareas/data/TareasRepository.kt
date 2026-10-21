package com.upchiapas.mistareas.data

import kotlinx.coroutines.flow.StateFlow

interface TareasRepository {
    val tareas: StateFlow<List<Tarea>>
    fun agregar(titulo: String)
    fun cambiarCompletada(id: Int)
    fun eliminar(id: Int)
}