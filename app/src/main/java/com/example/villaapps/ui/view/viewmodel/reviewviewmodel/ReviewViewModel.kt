package com.example.villaapps.ui.view.viewmodel.reviewviewmodel

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Review
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.repository.ReservasiRepository
import com.example.villaapps.repository.ReviewRepository
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.ReservasiUiState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ReviewUiState {
    data class Success(
        val review: List<Review>,
        val namaPelanggans: Map<Int, String>
    ) : ReviewUiState()
    object Error : ReviewUiState()
    object Loading : ReviewUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ReviewViewModel (
    private val reviewRepository: ReviewRepository,
    private val pelangganRepository: PelangganRepository,
    private val reservasiRepository: ReservasiRepository
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
            try {
                val reviews = reviewRepository.getAllReview().data

                val reservasiList = reservasiRepository.getAllReservasi().data

                val pelangganNames = mutableMapOf<Int, String>()
                for (reservasi in reservasiList) {
                    val pelanggan = pelangganRepository.getAllPelanggan().data
                        .find { it.idPelanggan == reservasi.idPelanggan }
                    pelanggan?.let {
                        pelangganNames[reservasi.idReservasi] = it.namaPelanggan
                    }
                }

                // Log data review dan pelanggan
                Log.d("ReviewViewModel", "Review count: ${reviews.size}")
                Log.d("ReviewViewModel", "Pelanggan names: $pelangganNames")

                // Set state dengan data yang telah didapatkan
                reviewUiState = ReviewUiState.Success(
                    review = reviews,
                    namaPelanggans = pelangganNames
                )
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Error: ${e.message}", e)
                reviewUiState = ReviewUiState.Error
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

