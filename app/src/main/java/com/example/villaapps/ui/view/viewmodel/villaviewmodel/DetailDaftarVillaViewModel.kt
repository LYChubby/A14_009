package com.example.villaapps.ui.view.viewmodel.villaviewmodel

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
import com.example.villaapps.repository.DaftarVillaRepository
import com.example.villaapps.ui.view.pages.villaview.DestinasiDetailVilla
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailDaftarVillaUiState {
    data class Success(val daftarVilla: DaftarVilla) : DetailDaftarVillaUiState()
    object Error : DetailDaftarVillaUiState()
    object Loading : DetailDaftarVillaUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailDaftarVillaViewModel(
    savedStateHandle: SavedStateHandle,
    private val daftarVillaRepository: DaftarVillaRepository
): ViewModel() {
    private val idVilla: Int = checkNotNull(savedStateHandle[DestinasiDetailVilla.IDVILLA])

    var detailDaftarVillaUiState: DetailDaftarVillaUiState by mutableStateOf(DetailDaftarVillaUiState.Loading)
        private set

    init {
        getDaftarVillaById()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getDaftarVillaById() {
        viewModelScope.launch {
            detailDaftarVillaUiState = DetailDaftarVillaUiState.Loading
            detailDaftarVillaUiState = try {
                val daftarVilla = daftarVillaRepository.getVillaById(idVilla)
                DetailDaftarVillaUiState.Success(daftarVilla)
            } catch (e: IOException) {
                DetailDaftarVillaUiState.Error
            } catch (e: HttpException) {
                DetailDaftarVillaUiState.Error
            }
        }
    }

    fun deleteDaftarVilla(idVilla: Int) {
        viewModelScope.launch {
            try {
                daftarVillaRepository.deleteVilla(idVilla)
            } catch (e: IOException) {
                DaftarVillaUiState.Error
            } catch (e: HttpException) {
                DaftarVillaUiState.Error
            }
        }
    }
}