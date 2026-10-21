package com.upchiapas.mistareas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.upchiapas.mistareas.data.TareasRepository

class TareasViewModelFactory(
    private val repository: TareasRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TareasViewModel::class.java)) {
            return TareasViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}