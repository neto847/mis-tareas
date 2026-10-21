package com.upchiapas.mistareas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upchiapas.mistareas.data.TareasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TareasViewModel(
    private val repository: TareasRepository
) : ViewModel() {

    // Backing property: privado se modifica, público solo se lee
    private val _uiState = MutableStateFlow(TareasUiState())
    val uiState: StateFlow<TareasUiState> = _uiState.asStateFlow()

    init {
        // Cada vez que cambia la lista del Repository, se actualiza el estado
        viewModelScope.launch {
            repository.tareas.collect {
                actualizarLista()
            }
        }
    }

    fun onTextoCambiado(texto: String) {
        _uiState.update { it.copy(textoNuevo = texto, hayError = false) }
    }

    fun agregarTarea() {
        val titulo = _uiState.value.textoNuevo.trim()
        if (titulo.isEmpty()) {
            _uiState.update { it.copy(hayError = true) }
            return
        }
        repository.agregar(titulo)
        _uiState.update { it.copy(textoNuevo = "", hayError = false) }
    }

    fun cambiarCompletada(id: Int) {
        repository.cambiarCompletada(id)
    }

    fun eliminarTarea(id: Int) {
        repository.eliminar(id)
    }

    fun cambiarFiltro(filtro: Filtro) {
        _uiState.update { it.copy(filtro = filtro) }
        actualizarLista()
    }

    // Aplica el filtro y calcula el contador de pendientes
    private fun actualizarLista() {
        val todas = repository.tareas.value
        val visibles = when (_uiState.value.filtro) {
            Filtro.TODAS -> todas
            Filtro.PENDIENTES -> todas.filter { !it.completada }
            Filtro.HECHAS -> todas.filter { it.completada }
        }
        _uiState.update {
            it.copy(
                tareas = visibles,
                pendientes = todas.count { tarea -> !tarea.completada }
            )
        }
    }
}