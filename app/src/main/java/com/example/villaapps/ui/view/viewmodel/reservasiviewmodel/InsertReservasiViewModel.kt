package com.example.villaapps.ui.view.viewmodel.reservasiviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Reservasi
import com.example.villaapps.repository.ReservasiRepository
import kotlinx.coroutines.launch

data class InsertReservasiUiState(
    val insertReservasiUiEvent: InsertReservasiUiEvent = InsertReservasiUiEvent()
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

class InsertReservasiViewModel (
    private val reservasiRepository: ReservasiRepository
): ViewModel() {
    var insertReservasiUiState by mutableStateOf(InsertReservasiUiState())
        private set

    fun updateInsertReservasiUiState(insertReservasiUiEvent: InsertReservasiUiEvent) {
        insertReservasiUiState = InsertReservasiUiState(insertReservasiUiEvent = insertReservasiUiEvent)
    }

    suspend fun insertReservasi() {
        viewModelScope.launch {
            try {
                reservasiRepository.insertReservasi(insertReservasiUiState.insertReservasiUiEvent.toReservasi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}