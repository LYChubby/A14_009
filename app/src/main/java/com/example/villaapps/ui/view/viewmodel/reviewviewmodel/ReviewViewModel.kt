package com.example.villaapps.ui.view.viewmodel.reviewviewmodel

import android.net.http.HttpException
import android.os.Build
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

sealed class ReviewUiState {
    data class Success(val review: List<Review>) : ReviewUiState()
    object Error : ReviewUiState()
    object Loading : ReviewUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ReviewViewModel (
    private val reviewRepository: ReviewRepository
): ViewModel() {
    var reviewUiState: ReviewUiState by mutableStateOf(ReviewUiState.Loading)
        private set

    init {
        getReview()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getReview() {
        viewModelScope.launch {
            reviewUiState = ReviewUiState.Loading
            reviewUiState = try {
                ReviewUiState.Success(reviewRepository.getAllReview().data)
            } catch (e: IOException) {
                ReviewUiState.Error
            } catch (e: HttpException) {
                ReviewUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteReview(idReview: Int) {
        viewModelScope.launch {
            try {
                reviewRepository.deleteReview(idReview)
            } catch (e: IOException) {
                ReviewUiState.Error
            } catch (e: HttpException) {
                ReviewUiState.Error
            }
        }
    }
 }

