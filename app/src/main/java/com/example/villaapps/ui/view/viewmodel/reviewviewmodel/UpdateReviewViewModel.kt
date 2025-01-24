package com.example.villaapps.ui.view.viewmodel.reviewviewmodel

import android.net.http.HttpException
import android.os.Build
import android.view.View
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Review
import com.example.villaapps.repository.ReviewRepository
import kotlinx.coroutines.launch
import java.io.IOException

data class UpdateReviewUiState(
    val updateReviewUiEvent: UpdateReviewUiEvent = UpdateReviewUiEvent()
)

data class UpdateReviewUiEvent(
    val idReview: Int = 0,
    val idReservasi: Int = 0,
    val nilai: String = "",
    val komentar: String = ""
)

fun UpdateReviewUiEvent.toReview(): Review = Review(
    idReview = idReview,
    idReservasi = idReservasi,
    nilai = nilai,
    komentar = komentar
)

fun Review.toUpdateReviewUiState(): UpdateReviewUiState = UpdateReviewUiState(
    updateReviewUiEvent = toUpdateReviewUiEvent()
)

fun Review.toUpdateReviewUiEvent(): UpdateReviewUiEvent = UpdateReviewUiEvent(
    idReview = idReview,
    idReservasi = idReservasi,
    nilai = nilai,
    komentar = komentar
)

class UpdateReviewViewModel (
    private val reviewRepository: ReviewRepository
): ViewModel() {
    var UpdateReviewUiState by mutableStateOf(UpdateReviewUiState())
        private set

    fun updateReviewState(updateReviewUiEvent: UpdateReviewUiEvent) {
        UpdateReviewUiState = UpdateReviewUiState(updateReviewUiEvent = updateReviewUiEvent)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadReview(idReview: Int) {
        viewModelScope.launch {
            try {
                val review = reviewRepository.getReviewById(idReview)
                UpdateReviewUiState = review.toUpdateReviewUiState()
            } catch (e: IOException) {
                e.printStackTrace()
            }catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updateReview() {
        viewModelScope.launch {
            try {
                val review = UpdateReviewUiState.updateReviewUiEvent.toReview()
                reviewRepository.updateReview(review.idReview, review)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}