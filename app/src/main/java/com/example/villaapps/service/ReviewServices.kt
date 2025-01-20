package com.example.villaapps.service

import com.example.villaapps.model.AllReviewResponse
import com.example.villaapps.model.DetailReviewResponse
import com.example.villaapps.model.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewServices {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertReview(@Body review: Review)

    @GET(".")
    suspend fun getAllReview(): AllReviewResponse

    @GET("{idReview}")
    suspend fun getReviewById(@Path("idReview") idReview: Int): DetailReviewResponse

    @PUT("{idReview}")
    suspend fun updateReview(@Path("idReview") idReview: Int, @Body review: Review)

    @DELETE("{idReview}")
    suspend fun deleteReview(@Path("idReview") idReview: Int): Response<Void>

}