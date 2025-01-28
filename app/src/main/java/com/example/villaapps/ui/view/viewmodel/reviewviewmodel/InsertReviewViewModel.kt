package com.example.villaapps.ui.view.viewmodel.reviewviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Review
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.repository.ReviewRepository
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.InsertReservasiUiEvent
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.isValid
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.toReservasi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
data class InsertReviewUiState(
    val insertReviewUiEvent: InsertReviewUiEvent = InsertReviewUiEvent(),
    val daftarPelanggan: List<Pair<Int, String>> = emptyList(),
)

data class InsertReviewUiEvent(
    val idReview: Int = 0,
    val idReservasi: Int = 0,
    val nilai: String = "",
    val komentar: String = ""
)

fun InsertReviewUiEvent.toReview(): Review = Review(
    idReview = idReview,
    idReservasi = idReservasi,
    nilai = nilai,
    komentar = komentar
)

fun Review.toInsertReviewUiState(): InsertReviewUiState = InsertReviewUiState(
    insertReviewUiEvent = toInsertReviewUiEvent()
)

fun Review.toInsertReviewUiEvent(): InsertReviewUiEvent = InsertReviewUiEvent(
    idReview = idReview,
    idReservasi = idReservasi,
    nilai = nilai,
    komentar = komentar
)

fun InsertReviewUiEvent.isValid(): Boolean {
    return idReview != 0 &&
            idReservasi != 0 &&
            nilai.isNotBlank() &&
            komentar.isNotBlank()
}

class InsertReviewViewModel (
    private val reviewRepository: ReviewRepository,
    private val pelangganRepository: PelangganRepository
): ViewModel() {
    var insertReviewuiState by mutableStateOf(InsertReviewUiState())
        private set

    private val _daftarPelanggan = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val daftarPelanggan: StateFlow<List<Pair<Int, String>>> = _daftarPelanggan

    init {
        fetchDaftarPelanggan()
    }

    private fun fetchDaftarPelanggan() {
        viewModelScope.launch {
            try {
                val pelangganData = pelangganRepository.getAllPelanggan().data
                _daftarPelanggan.value = pelangganData.map { it.idPelanggan to it.namaPelanggan }
                Log.d("InsertReviewViewModel", "Fetched Pelanggan Data: $pelangganData")
            } catch (e: Exception) {
                _daftarPelanggan.value = emptyList()
            }
        }
    }

    fun updateInsertReviewUiState(insertReviewUiEvent: InsertReviewUiEvent) {
        insertReviewuiState = InsertReviewUiState(insertReviewUiEvent = insertReviewUiEvent)
    }

    suspend fun insertReview(): Boolean {
        val currentState = insertReviewuiState.insertReviewUiEvent
        if (!currentState.isValid()) {
            return false
        }

        return try {
            reviewRepository.insertReview(currentState.toReview())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}