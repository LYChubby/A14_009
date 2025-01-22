package com.example.villaapps.ui.view.viewmodel.reservasiviewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Reservasi
import com.example.villaapps.repository.ReservasiRepository
import kotlinx.coroutines.launch
import java.io.IOException

data class UpdateReservasiUiState (
    val updateReservasiUiEvent: UpdateReservasiUiEvent = UpdateReservasiUiEvent()
)

data class UpdateReservasiUiEvent (
    val idReservasi: Int = 0,
    val idVilla: Int = 0,
    val idPelanggan: Int = 0,
    val checkIn: String = "",
    val checkOut: String = "",
    val jumlahKamar: Int = 0,
)

fun UpdateReservasiUiEvent.toReservasi(): Reservasi = Reservasi(
    idReservasi = idReservasi,
    idVilla = idVilla,
    idPelanggan = idPelanggan,
    checkIn = checkIn,
    checkOut = checkOut,
    jumlahKamar = jumlahKamar,
)

fun Reservasi.toUpdateReservasiUiState(): UpdateReservasiUiState = UpdateReservasiUiState(
    updateReservasiUiEvent = toUpdateReservasiUiEvent()
)

fun Reservasi.toUpdateReservasiUiEvent(): UpdateReservasiUiEvent = UpdateReservasiUiEvent(
    idReservasi = idReservasi,
    idVilla = idVilla,
    idPelanggan = idPelanggan,
    checkIn = checkIn,
    checkOut = checkOut,
    jumlahKamar = jumlahKamar,
)

class UpdateReservasiViewModel (
    private val reservasiRepository: ReservasiRepository
): ViewModel() {
    var UpdateReservasiUiState by mutableStateOf(UpdateReservasiUiState())
        private set

    fun updateReservasiState(updateReservasiUiEvent: UpdateReservasiUiEvent) {
        UpdateReservasiUiState = UpdateReservasiUiState(updateReservasiUiEvent = updateReservasiUiEvent)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadReservasi(idReservasi: Int) {
        viewModelScope.launch {
            try {
                val reservasi = reservasiRepository.getReservasiById(idReservasi)
                UpdateReservasiUiState = reservasi.toUpdateReservasiUiState()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updateReservasi() {
        viewModelScope.launch {
            try {
                val reservasi = UpdateReservasiUiState.updateReservasiUiEvent.toReservasi()
                reservasiRepository.updateReservasi(reservasi.idReservasi, reservasi)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}