package com.example.villaapps.ui.view.viewmodel.villaviewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.repository.DaftarVillaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DaftarVillaUiState {
    data class Success(val data: List<DaftarVilla>) : DaftarVillaUiState()
    object Error : DaftarVillaUiState()
    object Loading : DaftarVillaUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DaftarVillaViewModel(
    private val daftarVillaRepository: DaftarVillaRepository
): ViewModel() {
    var daftarVillaUiState: DaftarVillaUiState by mutableStateOf(DaftarVillaUiState.Loading)
        private set

    init {
        getDaftarVilla()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getDaftarVilla() {
        viewModelScope.launch {
            daftarVillaUiState = DaftarVillaUiState.Loading
            daftarVillaUiState = try {
                DaftarVillaUiState.Success(daftarVillaRepository.getAllVilla().data)
            } catch (e: IOException) {
                DaftarVillaUiState.Error
            } catch (e: HttpException) {
                DaftarVillaUiState.Error
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