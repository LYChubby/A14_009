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

sealed class PelangganUiState {
    data class Success(val pelanggan: List<Pelanggan>) : PelangganUiState()
    object Error : PelangganUiState()
    object Loading : PelangganUiState()

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PelangganViewModel (
    private val pelangganRepository: PelangganRepository
): ViewModel() {
    var pelangganUiState: PelangganUiState by mutableStateOf(PelangganUiState.Loading)
        private set

    init {
        getPelanggan()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getPelanggan() {
        viewModelScope.launch {
            pelangganUiState = PelangganUiState.Loading
            pelangganUiState = try {
                PelangganUiState.Success(pelangganRepository.getAllPelanggan().data)
            } catch (e: IOException) {
                PelangganUiState.Error
            } catch (e: HttpException) {
                PelangganUiState.Error
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