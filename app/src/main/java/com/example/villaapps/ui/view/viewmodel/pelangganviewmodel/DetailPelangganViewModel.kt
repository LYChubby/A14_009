package com.example.villaapps.ui.view.viewmodel.pelangganviewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.ui.view.pages.pelangganview.DestinasiDetailPelanggan
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPelangganUiState {
    data class Success(val pelanggan: Pelanggan) : DetailPelangganUiState()
    object Error : DetailPelangganUiState()
    object Loading : DetailPelangganUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailPelangganViewModel (
    savedStateHandle: SavedStateHandle,
    private val pelangganRepository: PelangganRepository
): ViewModel() {
    private val idPelanggan: Int = checkNotNull(savedStateHandle[DestinasiDetailPelanggan.IDPELANGGAN])

    var detailPelangganUiState: DetailPelangganUiState by mutableStateOf(DetailPelangganUiState.Loading)
        private set

    init {
        getPelangganById()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getPelangganById() {
        viewModelScope.launch {
            detailPelangganUiState = DetailPelangganUiState.Loading
            detailPelangganUiState = try {
                val pelanggan = pelangganRepository.getPelangganById(idPelanggan)
                DetailPelangganUiState.Success(pelanggan)
            } catch (e: IOException) {
                DetailPelangganUiState.Error
            } catch (e: HttpException) {
                DetailPelangganUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deletePelanggan(idPelanggan: Int) {
        viewModelScope.launch {
            try {
                pelangganRepository.deletePelanggan(idPelanggan)
            } catch (e: IOException) {
                PelangganUiState.Error
            } catch (e: HttpException) {
                PelangganUiState.Error
            }
        }
    }
}