package com.example.villaapps.ui.view.viewmodel.reservasiviewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.model.Reservasi
import com.example.villaapps.repository.ReservasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ReservasiUiState {
    data class Success(val resrvasi: List<Reservasi>) : ReservasiUiState()
    object Error : ReservasiUiState()
    object Loading : ReservasiUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ReservasiViewModel(
    private val reservasiRepository: ReservasiRepository
) : ViewModel() {

    private val _pelangganMap = MutableStateFlow<Map<Int, Pelanggan>>(emptyMap())
    val pelangganMap: StateFlow<Map<Int, Pelanggan>> = _pelangganMap.asStateFlow()

    private val _villaMap = MutableStateFlow<Map<Int, DaftarVilla>>(emptyMap())
    val villaMap: StateFlow<Map<Int, DaftarVilla>> = _villaMap.asStateFlow()

    var reservasiUiState: ReservasiUiState by mutableStateOf(ReservasiUiState.Loading)
        private set

    init {
        getReservasi()
        loadDaftarVilla()
        loadDaftarPelanggan()
    }

    private fun loadDaftarVilla() {
        viewModelScope.launch {
            try {
                val response = reservasiRepository.getDaftarVilla()
                if (response.status) {
                    _villaMap.value = response.data.associateBy { it.idVilla }
                }
            } catch (e: Exception) {
                ReservasiUiState.Error
            }
        }
    }

    private fun loadDaftarPelanggan() {
        viewModelScope.launch {
            try {
                val response = reservasiRepository.getDaftarPelanggan()
                if (response.status) {
                    _pelangganMap.value = response.data.associateBy { it.idPelanggan }
                }
            } catch (e: Exception) {
                ReservasiUiState.Error
            }
        }
    }

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

    fun deleteReservasi(idReservasi: Int) {
        viewModelScope.launch {
            try {
                reservasiRepository.deleteReservasi(idReservasi)
                getReservasi()
            } catch (e: IOException) {
                ReservasiUiState.Error
            } catch (e: HttpException) {
                ReservasiUiState.Error
            }
        }
    }
}
