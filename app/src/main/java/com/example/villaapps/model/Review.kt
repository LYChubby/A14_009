package com.example.villaapps.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review (

    @SerialName("id_review")
    val idReview: Int,

    @SerialName("id_reservasi")
    val idReservasi: Int,

    val nilai: String,

    val komentar: String,

)

@Serializable
data class AllReviewResponse (
    val status: Boolean,
    val message: String,
    val data: List<Review>,
)

@Serializable
data class DetailReviewResponse (
    val status: Boolean,
    val message: String,
    val data: Review,
)