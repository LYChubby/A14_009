package com.example.villaapps.ui.view.viewmodel.reviewviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Review
import com.example.villaapps.repository.ReviewRepository
import kotlinx.coroutines.launch
data class InsertReviewUiState(
    val insertReviewUiEvent: InsertReviewUiEvent = InsertReviewUiEvent(),
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

class InsertReviewViewModel (
    private val reviewRepository: ReviewRepository
): ViewModel() {
    var insertReviewuiState by mutableStateOf(InsertReviewUiState())
        private set

    fun updateInsertReviewUiState(insertReviewUiEvent: InsertReviewUiEvent) {
        insertReviewuiState = InsertReviewUiState(insertReviewUiEvent = insertReviewUiEvent)
    }

    suspend fun insertReview() {
        viewModelScope.launch {
            try {
                reviewRepository.insertReview(insertReviewuiState.insertReviewUiEvent.toReview())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}