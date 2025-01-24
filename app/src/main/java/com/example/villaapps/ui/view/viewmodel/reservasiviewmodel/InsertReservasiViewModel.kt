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

class InsertReservasiViewModel(
    private val reservasiRepository: ReservasiRepository,
    private val daftarVillaRepository: DaftarVillaRepository,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {

    var insertReservasiUiState by mutableStateOf(InsertReservasiUiState())
        private set

    init {
        fetchDaftarVilla()
        fetchDaftarPelanggan()
    }

    private fun fetchDaftarVilla() {
        viewModelScope.launch {
            try {
                val villaData = daftarVillaRepository.getAllVilla().data
                insertReservasiUiState = insertReservasiUiState.copy(
                    daftarVilla = villaData.map { it.idVilla to it.namaVilla }
                )
            } catch (e: Exception) {
                insertReservasiUiState = insertReservasiUiState.copy(daftarVilla = emptyList())
                e.printStackTrace()
            }
        }
    }

    private fun fetchDaftarPelanggan() {
        viewModelScope.launch {
            try {
                val pelangganData = pelangganRepository.getAllPelanggan().data
                insertReservasiUiState = insertReservasiUiState.copy(
                    daftarPelanggan = pelangganData.map { it.idPelanggan to it.namaPelanggan }
                )
            } catch (e: Exception) {
                insertReservasiUiState = insertReservasiUiState.copy(daftarPelanggan = emptyList())
                e.printStackTrace()
            }
        }
    }

    fun updateInsertReservasiUiState(insertReservasiUiEvent: InsertReservasiUiEvent) {
        insertReservasiUiState = insertReservasiUiState.copy(insertReservasiUiEvent = insertReservasiUiEvent)
    }

    suspend fun insertReservasi() {
        try {
            reservasiRepository.insertReservasi(insertReservasiUiState.insertReservasiUiEvent.toReservasi())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}