package com.example.villaapps.ui.view.viewmodel.pelangganviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.repository.PelangganRepository
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

class InsertPelangganViewModel(
    private val pelangganRepository: PelangganRepository
): ViewModel() {

    private val _daftarPelanggan = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val daftarPelanggan: StateFlow<List<Pair<Int, String>>> = _daftarPelanggan

    init {
        viewModelScope.launch {
            try {
                val response = pelangganRepository.getAllPelanggan()
                _daftarPelanggan.value = response.data.map { it.idPelanggan to it.namaPelanggan }
            } catch (e: Exception) {
                _daftarPelanggan.value = emptyList()
            }
        }
    }

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
