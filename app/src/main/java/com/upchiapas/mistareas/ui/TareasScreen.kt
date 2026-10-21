package com.upchiapas.mistareas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upchiapas.mistareas.R

// Esta función conecta el ViewModel con la pantalla
@Composable
fun TareasScreen(
    viewModel: TareasViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TareasContent(
        uiState = uiState,
        onTextoCambiado = viewModel::onTextoCambiado,
        onAgregar = viewModel::agregarTarea,
        onCambiarCompletada = viewModel::cambiarCompletada,
        onEliminar = viewModel::eliminarTarea,
        onCambiarFiltro = viewModel::cambiarFiltro,
        modifier = modifier
    )
}

// Esta función solo dibuja: recibe estado y avisa eventos con lambdas
@Composable
fun TareasContent(
    uiState: TareasUiState,
    onTextoCambiado: (String) -> Unit,
    onAgregar: () -> Unit,
    onCambiarCompletada: (Int) -> Unit,
    onEliminar: (Int) -> Unit,
    onCambiarFiltro: (Filtro) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.titulo_app),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.contador_pendientes, uiState.pendientes),
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = uiState.textoNuevo,
            onValueChange = onTextoCambiado,
            label = { Text(stringResource(R.string.hint_nueva_tarea)) },
            isError = uiState.hayError,
            supportingText = {
                if (uiState.hayError) {
                    Text(stringResource(R.string.error_titulo_vacio))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onAgregar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.boton_agregar))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Filtro.entries.forEach { filtro ->
                FilterChip(
                    selected = uiState.filtro == filtro,
                    onClick = { onCambiarFiltro(filtro) },
                    label = { Text(stringResource(textoFiltro(filtro))) }
                )
            }
        }

        if (uiState.tareas.isEmpty()) {
            Text(text = stringResource(R.string.lista_vacia))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.tareas, key = { it.id }) { tarea ->
                    TareaItem(
                        tarea = tarea,
                        onCambiarCompletada = { onCambiarCompletada(tarea.id) },
                        onEliminar = { onEliminar(tarea.id) }
                    )
                }
            }
        }
    }
}

private fun textoFiltro(filtro: Filtro): Int {
    return when (filtro) {
        Filtro.TODAS -> R.string.filtro_todas
        Filtro.PENDIENTES -> R.string.filtro_pendientes
        Filtro.HECHAS -> R.string.filtro_hechas
    }
}