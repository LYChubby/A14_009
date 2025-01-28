package com.example.villaapps.ui.view.viewmodel.pelangganviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.isValid
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.toReservasi
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.InsertDaftarVillaUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

fun InsertPelangganUiEvent.isValid(): Boolean {
    return idPelanggan != 0 &&
            namaPelanggan.isNotBlank() &&
            noHp.isNotBlank()
}

class InsertPelangganViewModel(
    private val pelangganRepository: PelangganRepository
): ViewModel() {

    var insertPelangganUiState by mutableStateOf(InsertPelangganUiState())
        private set

    fun updateInsertPelangganUiState(insertPelangganUiEvent: InsertPelangganUiEvent) {
        insertPelangganUiState = InsertPelangganUiState(insertPelangganUiEvent = insertPelangganUiEvent)
    }

    suspend fun insertPelanggan(): Boolean {
        val currentState = insertPelangganUiState.insertPelangganUiEvent
        if (!currentState.isValid()) {
            return false
        }

        return try {
            pelangganRepository.insertPelanggan(currentState.toPelanggan())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
