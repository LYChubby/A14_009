package com.example.villaapps.repository

import com.example.villaapps.model.AllReviewResponse
import com.example.villaapps.model.Review
import com.example.villaapps.service.ReviewServices

interface ReviewRepository {

    suspend fun insertReview(review: Review)

    suspend fun getAllReview(): AllReviewResponse

    suspend fun updateReview(idReview: Int, review: Review)

    suspend fun deleteReview(idReview: Int)

    suspend fun getReviewById(idReview: Int): Review
}

class NetworkReviewRepository (
    private val reviewApiService: ReviewServices
): ReviewRepository {

    override suspend fun insertReview(review: Review) {
        reviewApiService.insertReview(review)
    }

    override suspend fun updateReview(idReview: Int, review: Review) {
        reviewApiService.updateReview(idReview, review)
    }

    override suspend fun deleteReview(idReview: Int) {
        try {
            val response = reviewApiService.deleteReview(idReview)
            if (!response.isSuccessful) {
                throw Exception("Failed to Delete Review. HTTP Status Code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllReview(): AllReviewResponse =
        reviewApiService.getAllReview()

    override suspend fun getReviewById(idReview: Int): Review {
        return reviewApiService.getReviewById(idReview).data
    }
}