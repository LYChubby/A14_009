package com.example.villaapps.ui.view.viewmodel.villaviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.repository.DaftarVillaRepository
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.InsertReservasiUiEvent
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.InsertReviewUiEvent
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.isValid
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.toReview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class InsertDaftarVillaUiState (
    val insertDaftarVillaUiEvent: InsertDaftarVillaUiEvent = InsertDaftarVillaUiEvent(),
)

data class InsertDaftarVillaUiEvent (
    val idVilla: Int = 0,
    val namaVilla: String = "",
    val alamat: String = "",
    val kamarTersedia: Int = 0,
)

fun InsertDaftarVillaUiEvent.toDaftarVilla(): DaftarVilla = DaftarVilla(
    idVilla = idVilla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamarTersedia = kamarTersedia,
)

fun DaftarVilla.toInsertDaftarVillaUiState(): InsertDaftarVillaUiState = InsertDaftarVillaUiState(
    insertDaftarVillaUiEvent = toInsertDaftarVillaUiEvent()
)

fun DaftarVilla.toInsertDaftarVillaUiEvent(): InsertDaftarVillaUiEvent = InsertDaftarVillaUiEvent(
    idVilla = idVilla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamarTersedia = kamarTersedia,
)

fun InsertDaftarVillaUiEvent.isValid(): Boolean {
    return idVilla != 0 &&
            namaVilla.isNotBlank() &&
            alamat.isNotBlank() &&
            kamarTersedia > 0
}

class InsertDaftarVillaViewModel (
    private val daftarVillaRepository: DaftarVillaRepository
): ViewModel() {

    var insertDaftarVillaUiState by mutableStateOf(InsertDaftarVillaUiState())
        private set

    fun updateInsertDaftarVillaUiState(insertDaftarVillaUiEvent: InsertDaftarVillaUiEvent) {
        insertDaftarVillaUiState = InsertDaftarVillaUiState(insertDaftarVillaUiEvent = insertDaftarVillaUiEvent)
    }

    suspend fun insertDaftarVilla(): Boolean {
        val currentState = insertDaftarVillaUiState.insertDaftarVillaUiEvent
        if (!currentState.isValid()) {
            return false
        }

        return try {
            daftarVillaRepository.insertVilla(currentState.toDaftarVilla())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}