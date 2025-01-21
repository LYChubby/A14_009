package com.example.villaapps.ui.view.viewmodel.pelangganviewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.repository.PelangganRepository
import kotlinx.coroutines.launch
import java.io.IOException

data class UpdatePelangganUiState(
    val updatePelangganUiEvent: UpdatePelangganUiEvent = UpdatePelangganUiEvent()
)

data class UpdatePelangganUiEvent(
    val idPelanggan: Int = 0,
    val namaPelanggan: String = "",
    val noHp: String = "",
)

fun UpdatePelangganUiEvent.toPelanggan(): Pelanggan = Pelanggan(
    idPelanggan = idPelanggan,
    namaPelanggan = namaPelanggan,
    noHp = noHp
)

fun Pelanggan.toUpdatePelangganUiState(): UpdatePelangganUiState = UpdatePelangganUiState(
    updatePelangganUiEvent = toUpdatePelangganUiEvent()
)

fun Pelanggan.toUpdatePelangganUiEvent(): UpdatePelangganUiEvent = UpdatePelangganUiEvent(
    idPelanggan = idPelanggan,
    namaPelanggan = namaPelanggan,
    noHp = noHp
)

class UpdatePelangganViewModel (
    private val pelangganRepository: PelangganRepository
): ViewModel() {
    var UpdatePelangganUiState by mutableStateOf(UpdatePelangganUiState())
        private set

    fun updateUpdatePelangganState(updatePelangganUiEvent: UpdatePelangganUiEvent) {
        UpdatePelangganUiState = UpdatePelangganUiState(updatePelangganUiEvent = updatePelangganUiEvent)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadPelanggan(idPelanggan: Int) {
        viewModelScope.launch {
            try {
                val pelanggan = pelangganRepository.getPelangganById(idPelanggan)
                UpdatePelangganUiState = pelanggan.toUpdatePelangganUiState()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updatePelanggan() {
        viewModelScope.launch {
            try {
                val pelanggan = UpdatePelangganUiState.updatePelangganUiEvent.toPelanggan()
                pelangganRepository.updatePelanggan(pelanggan.idPelanggan, pelanggan)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}