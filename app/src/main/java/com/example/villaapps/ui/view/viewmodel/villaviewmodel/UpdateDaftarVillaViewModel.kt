package com.example.villaapps.ui.view.viewmodel.villaviewmodel

import android.net.http.HttpException
import android.os.Build
import android.view.View
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

data class UpdateDaftarVillaUiState(
    val updateDaftarVillaUiEvent: UpdateDaftarVillaUiEvent = UpdateDaftarVillaUiEvent()
)

data class UpdateDaftarVillaUiEvent(
    val idVilla: Int = 0,
    val namaVilla: String = "",
    val alamat: String = "",
    val kamarTersedia: Int = 0,
)

fun UpdateDaftarVillaUiEvent.toDaftarVilla(): DaftarVilla = DaftarVilla(
    idVilla = idVilla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamarTersedia = kamarTersedia,
)

fun DaftarVilla.toUpdateDaftarVillaUiState(): UpdateDaftarVillaUiState = UpdateDaftarVillaUiState(
    updateDaftarVillaUiEvent = toUpdateDaftarVillaUiEvent()
)

fun DaftarVilla.toUpdateDaftarVillaUiEvent(): UpdateDaftarVillaUiEvent = UpdateDaftarVillaUiEvent(
    idVilla = idVilla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamarTersedia = kamarTersedia,
)

class UpdateDaftarVillaViewModel(
    private val daftarVillaRepository: DaftarVillaRepository
): ViewModel() {
    var UpdateDaftarVillaUiState by mutableStateOf(UpdateDaftarVillaUiState())
        private set

    fun UpdateDaftarVillaState(updateDaftarVillaUiEvent: UpdateDaftarVillaUiEvent) {
        UpdateDaftarVillaUiState = UpdateDaftarVillaUiState(updateDaftarVillaUiEvent = updateDaftarVillaUiEvent)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadDaftarVilla(idVilla: Int) {
        viewModelScope.launch {
            try {
                val daftarVilla = daftarVillaRepository.getVillaById(idVilla)
                UpdateDaftarVillaUiState = daftarVilla.toUpdateDaftarVillaUiState()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updateDaftarVilla() {
        viewModelScope.launch {
            try {
                val daftarVilla = UpdateDaftarVillaUiState.updateDaftarVillaUiEvent.toDaftarVilla()
                daftarVillaRepository.updateVilla(daftarVilla.idVilla, daftarVilla)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}