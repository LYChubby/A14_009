package com.example.villaapps.ui.view.viewmodel.reservasiviewmodel

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Reservasi
import com.example.villaapps.repository.DaftarVillaRepository
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.repository.ReservasiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ReservasiUiState {
    data class Success(
        val reservasi: List<Reservasi>,
        val namaVillas: Map<Int, String>,
        val namaPelanggans: Map<Int, String>
    ) : ReservasiUiState()
    object Error : ReservasiUiState()
    object Loading : ReservasiUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ReservasiViewModel(
    private val reservasiRepository: ReservasiRepository,
    private val villaRepository: DaftarVillaRepository,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {
    var reservasiUiState: ReservasiUiState by mutableStateOf(ReservasiUiState.Loading)
        private set

    init {
        getReservasi()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getReservasi() {
        viewModelScope.launch {
            try {
                val reservasiList = reservasiRepository.getAllReservasi().data
                val villaNames = villaRepository.getAllVilla().data.associate { villa ->
                    Log.d("Villa Data", "ID: ${villa.idVilla}, Name: ${villa.namaVilla}")
                    villa.idVilla to villa.namaVilla
                }
                val pelangganNames = pelangganRepository.getAllPelanggan().data.associate { pelanggan ->
                    Log.d("Pelanggan Data", "ID: ${pelanggan.idPelanggan}, Name: ${pelanggan.namaPelanggan}")
                    pelanggan.idPelanggan to pelanggan.namaPelanggan
                }


                // Log sizes and contents
                Log.d("ReservasiViewModel", "Reservasi count: ${reservasiList.size}")
                Log.d("ReservasiViewModel", "Villa names: $villaNames")
                Log.d("ReservasiViewModel", "Pelanggan names: $pelangganNames")

                reservasiUiState = ReservasiUiState.Success(
                    reservasi = reservasiList,
                    namaVillas = villaNames,
                    namaPelanggans = pelangganNames
                )
            } catch (e: Exception) {
                Log.e("ReservasiViewModel", "Error: ${e.message}", e)
                reservasiUiState = ReservasiUiState.Error
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
