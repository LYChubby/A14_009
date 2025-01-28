package com.example.villaapps.ui.view.viewmodel.reviewviewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villaapps.model.Review
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.repository.ReservasiRepository
import com.example.villaapps.repository.ReviewRepository
import com.example.villaapps.ui.view.pages.reviewview.DestinasiDetailReview
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailReviewUiState {
    data class Success(
        val review: Review,
        val namaPelanggans: String
    ) : DetailReviewUiState()
    object Error : DetailReviewUiState()
    object Loading : DetailReviewUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailReviewViewModel (
    savedStateHandle: SavedStateHandle,
    private val reviewRepository: ReviewRepository,
    private val pelangganRepository: PelangganRepository,
    private val reservasiRepository: ReservasiRepository
): ViewModel() {
    private val idReview: Int = checkNotNull(savedStateHandle[DestinasiDetailReview.IDREVIEW])

    var detailReviewUiState: DetailReviewUiState by mutableStateOf(DetailReviewUiState.Loading)
        private set

    init {
        getReviewById()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getReviewById() {
        viewModelScope.launch {
            detailReviewUiState = DetailReviewUiState.Loading
            try {
                val review = reviewRepository.getReviewById(idReview)

                val reservasi = reservasiRepository.getReservasiById(review.idReservasi)

                val namaPelanggan = pelangganRepository.getAllPelanggan().data
                    .find { it.idPelanggan == reservasi.idPelanggan }?.namaPelanggan
                    ?: "Pelanggan Tidak Diketahui"

                detailReviewUiState = DetailReviewUiState.Success(
                    review = review,
                    namaPelanggans = namaPelanggan
                )
            } catch (e: IOException) {
                detailReviewUiState = DetailReviewUiState.Error
            } catch (e: HttpException) {
                detailReviewUiState = DetailReviewUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteReview(idReview: Int) {
        viewModelScope.launch {
            try {
                reviewRepository.deleteReview(idReview)
            } catch (e: IOException) {
                DetailReviewUiState.Error
            } catch (e: HttpException) {
                DetailReviewUiState.Error
            }
        }
    }
}