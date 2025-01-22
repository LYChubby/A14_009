package com.example.villaapps.ui.view.viewmodel.reservasiviewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.model.Reservasi
import com.example.villaapps.repository.ReservasiRepository
import com.example.villaapps.ui.view.pages.reservasiview.DestinasiDetailReservasi
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailReservasiUiState {
    data class Success(
        val reservasi: Reservasi,
        val villa: DaftarVilla,
        val pelanggan: Pelanggan
    ) : DetailReservasiUiState()
    object Error : DetailReservasiUiState()
    object Loading : DetailReservasiUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailReservasiViewModel (
    savedStateHandle: SavedStateHandle,
    private val reservasiRepository: ReservasiRepository
): ViewModel() {
    private val idReservasi: Int = checkNotNull(savedStateHandle[DestinasiDetailReservasi.IDRESERVASI])

    var detailReservasiUiState: DetailReservasiUiState by mutableStateOf(DetailReservasiUiState.Loading)
        private set

    init {
        getReservasiById()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getReservasiById() {
        viewModelScope.launch {
            detailReservasiUiState = DetailReservasiUiState.Loading
            detailReservasiUiState = try {
                val reservasi = reservasiRepository.getReservasiById(idReservasi)

                val daftarVilla = reservasiRepository.getDaftarVilla().data
                val daftarPelanggan = reservasiRepository.getDaftarPelanggan().data

                val villa = daftarVilla.firstOrNull { it.idVilla == reservasi.idVilla }
                    ?: throw Exception("Villa not found")
                val pelanggan = daftarPelanggan.firstOrNull { it.idPelanggan == reservasi.idPelanggan }
                    ?: throw Exception("Pelanggan not found")

                DetailReservasiUiState.Success(reservasi, villa, pelanggan)
            } catch (e: IOException) {
                DetailReservasiUiState.Error
            } catch (e: HttpException) {
                DetailReservasiUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteReservasi(idReservasi: Int) {
        viewModelScope.launch {
            try {
                reservasiRepository.deleteReservasi(idReservasi)
            } catch (e: IOException) {
                ReservasiUiState.Error
            } catch (e: HttpException) {
                ReservasiUiState.Error
            }
        }
    }
}