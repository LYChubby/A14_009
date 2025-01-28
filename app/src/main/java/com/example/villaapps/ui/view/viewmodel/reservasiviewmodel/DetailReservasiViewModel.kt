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
import com.example.villaapps.model.Reservasi
import com.example.villaapps.repository.DaftarVillaRepository
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.repository.ReservasiRepository
import com.example.villaapps.ui.view.pages.reservasiview.DestinasiDetailReservasi
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailReservasiUiState {
    data class Success(
        val reservasi: Reservasi,
        val namaVillas: String,
        val namaPelanggans: String
    ) : DetailReservasiUiState()
    object Error : DetailReservasiUiState()
    object Loading : DetailReservasiUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailReservasiViewModel (
    savedStateHandle: SavedStateHandle,
    private val reservasiRepository: ReservasiRepository,
    private val villaRepository: DaftarVillaRepository,
    private val pelangganRepository: PelangganRepository
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
            try {
                val reservasi = reservasiRepository.getReservasiById(idReservasi)

                val namaVilla = villaRepository.getAllVilla().data
                    .find { it.idVilla == reservasi.idVilla }?.namaVilla
                    ?: "Villa Tidak Diketahui"

                val namaPelanggan = pelangganRepository.getAllPelanggan().data
                    .find { it.idPelanggan == reservasi.idPelanggan }?.namaPelanggan
                    ?: "Pelanggan Tidak Diketahui"

                detailReservasiUiState = DetailReservasiUiState.Success(
                    reservasi = reservasi,
                    namaVillas = namaVilla,
                    namaPelanggans = namaPelanggan
                )
            } catch (e: IOException) {
                detailReservasiUiState = DetailReservasiUiState.Error
            } catch (e: HttpException) {
                detailReservasiUiState = DetailReservasiUiState.Error
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