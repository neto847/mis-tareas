package com.upchiapas.mistareas.ui

import com.upchiapas.mistareas.data.Tarea

data class TareasUiState(
    val tareas: List<Tarea> = emptyList(),
    val filtro: Filtro = Filtro.TODAS,
    val textoNuevo: String = "",
    val hayError: Boolean = false,
    val pendientes: Int = 0
)