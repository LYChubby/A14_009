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

sealed class ReservasiUiState {
    data class Success(val resrvasi: List<Reservasi>) : ReservasiUiState()
    object Error : ReservasiUiState()
    object Loading : ReservasiUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ReservasiViewModel (
    private val reservasiRepository: ReservasiRepository
): ViewModel() {
    var reservasiUiState: ReservasiUiState by mutableStateOf(ReservasiUiState.Loading)
        private set

    init {
        getReservasi()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getReservasi() {
        viewModelScope.launch {
            reservasiUiState = ReservasiUiState.Loading
            reservasiUiState = try {
                ReservasiUiState.Success(reservasiRepository.getAllReservasi().data)
            } catch (e: IOException) {
                ReservasiUiState.Error
            } catch (e: HttpException) {
                ReservasiUiState.Error
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