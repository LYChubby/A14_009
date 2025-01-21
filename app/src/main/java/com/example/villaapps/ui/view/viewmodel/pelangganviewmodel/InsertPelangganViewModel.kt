package com.example.villaapps.ui.view.viewmodel.pelangganviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.repository.PelangganRepository
import kotlinx.coroutines.launch

data class InsertPelangganUiState(
    val insertPelangganUiEvent: InsertPelangganUiEvent = InsertPelangganUiEvent()
)

data class InsertPelangganUiEvent(
    val idPelanggan: Int = 0,
    val namaPelanggan: String = "",
    val noHp: String = "",
)

fun InsertPelangganUiEvent.toPelanggan(): Pelanggan = Pelanggan(
    idPelanggan = idPelanggan,
    namaPelanggan = namaPelanggan,
    noHp = noHp
)

fun Pelanggan.toInsertPelangganUiState(): InsertPelangganUiState = InsertPelangganUiState(
    insertPelangganUiEvent = toInsertPelangganUiEvent()
)

fun Pelanggan.toInsertPelangganUiEvent(): InsertPelangganUiEvent = InsertPelangganUiEvent(
    idPelanggan = idPelanggan,
    namaPelanggan = namaPelanggan,
    noHp = noHp
)

class InsertPelangganViewModel(
    private val pelangganRepository: PelangganRepository
): ViewModel() {
    var insertPelangganUiState by mutableStateOf(InsertPelangganUiState())
        private set

    fun updateInsertPelangganUiState(insertPelangganUiEvent: InsertPelangganUiEvent) {
        insertPelangganUiState = InsertPelangganUiState(insertPelangganUiEvent = insertPelangganUiEvent)
    }

    suspend fun insertPelanggan() {
        viewModelScope.launch {
            try {
                pelangganRepository.insertPelanggan(insertPelangganUiState.insertPelangganUiEvent.toPelanggan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
