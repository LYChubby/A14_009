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
import com.example.villaapps.repository.ReservasiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailReservasiUiState {
    data class Success(val reservasi: Reservasi) : DetailReservasiUiState()
    object Error : DetailReservasiUiState()
    object Loading : DetailReservasiUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailReservasiViewModel (
    savedStateHandle: SavedStateHandle,
    private val reservasiRepository: ReservasiRepository
): ViewModel() {
    private val idReservasi: Int = checkNotNull(savedStateHandle[DestinasiDetailReservasi.IDPELANGGAN])

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
                DetailReservasiUiState.Success(reservasi)
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