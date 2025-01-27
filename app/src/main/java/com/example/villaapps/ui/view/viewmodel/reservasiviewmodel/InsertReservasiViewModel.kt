package com.example.villaapps.ui.view.viewmodel.reservasiviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.Reservasi
import com.example.villaapps.repository.DaftarVillaRepository
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.repository.ReservasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class InsertReservasiUiState(
    val insertReservasiUiEvent: InsertReservasiUiEvent = InsertReservasiUiEvent(),
    val daftarVilla: List<Pair<Int, String>> = emptyList(),
    val daftarPelanggan: List<Pair<Int, String>> = emptyList(),
)

data class InsertReservasiUiEvent(
    val idReservasi: Int = 0,
    val idVilla: Int = 0,
    val idPelanggan: Int = 0,
    val checkIn: String = "",
    val checkOut: String = "",
    val jumlahKamar: Int = 0,
)

fun InsertReservasiUiEvent.toReservasi(): Reservasi = Reservasi(
    idReservasi = idReservasi,
    idVilla = idVilla,
    idPelanggan = idPelanggan,
    checkIn = checkIn,
    checkOut = checkOut,
    jumlahKamar = jumlahKamar,
)

fun Reservasi.toInsertReservasiUiState(): InsertReservasiUiState = InsertReservasiUiState(
    insertReservasiUiEvent = toInsertReservasiUiEvent()
)

fun Reservasi.toInsertReservasiUiEvent(): InsertReservasiUiEvent = InsertReservasiUiEvent(
    idReservasi = idReservasi,
    idVilla = idVilla,
    idPelanggan = idPelanggan,
    checkIn = checkIn,
    checkOut = checkOut,
)

fun InsertReservasiUiEvent.isValid(): Boolean {
    return idVilla != 0 &&
            idPelanggan != 0 &&
            checkIn.isNotBlank() &&
            checkOut.isNotBlank() &&
            jumlahKamar > 0
}

class InsertReservasiViewModel(
    private val reservasiRepository: ReservasiRepository,
    private val daftarVillaRepository: DaftarVillaRepository,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {

    var insertReservasiUiState by mutableStateOf(InsertReservasiUiState())
        private set

    private val _daftarVilla = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val daftarVilla: StateFlow<List<Pair<Int, String>>> = _daftarVilla

    private val _daftarPelanggan = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val daftarPelanggan: StateFlow<List<Pair<Int, String>>> = _daftarPelanggan

    init {
        fetchDaftarVilla()
        fetchDaftarPelanggan()
    }

    private fun fetchDaftarVilla() {
        viewModelScope.launch {
            try {
                val villaData = daftarVillaRepository.getAllVilla().data
                _daftarVilla.value = villaData.map { it.idVilla to it.namaVilla }
                Log.d("InsertReservasiViewModel", "Fetched Villa Data: $villaData")
            } catch (e: Exception) {
                _daftarVilla.value = emptyList()
            }
        }
    }

    private fun fetchDaftarPelanggan() {
        viewModelScope.launch {
            try {
                val pelangganData = pelangganRepository.getAllPelanggan().data
                _daftarPelanggan.value = pelangganData.map { it.idPelanggan to it.namaPelanggan }
                Log.d("InsertReservasiViewModel", "Fetched Pelanggan Data: $pelangganData")
            } catch (e: Exception) {
                _daftarPelanggan.value = emptyList()
            }
        }
    }

    fun updateInsertReservasiUiState(insertReservasiUiEvent: InsertReservasiUiEvent) {
        insertReservasiUiState = insertReservasiUiState.copy(insertReservasiUiEvent = insertReservasiUiEvent)
    }

    suspend fun insertReservasi(): Boolean {
        val currentState = insertReservasiUiState.insertReservasiUiEvent
        if (!currentState.isValid()) {
            return false
        }

        return try {
            reservasiRepository.insertReservasi(currentState.toReservasi())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}