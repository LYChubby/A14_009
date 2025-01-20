package com.example.villaapps.container

import com.example.villaapps.repository.NetworkReviewRepository
import com.example.villaapps.repository.ReviewRepository
import com.example.villaapps.service.ReviewServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppReviewContainer {
    val reviewRepository: ReviewRepository
}

class ReviewContainer : AppReviewContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/review/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val reviewService: ReviewServices by lazy {
        retrofit.create(ReviewServices::class.java)
    }

    override val reviewRepository: ReviewRepository by lazy {
        NetworkReviewRepository(reviewService)
    }
}