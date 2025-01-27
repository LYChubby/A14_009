package com.example.villaapps.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DaftarVilla (
    @SerialName("id_villa")
    val idVilla: Int,

    @SerialName("nama_villa")
    val namaVilla: String ="",

    val alamat: String ="",

    @SerialName("kamar_tersedia")
    val kamarTersedia: Int = 0
)

@Serializable
data class DaftarVillaResponse (
    val status: Boolean,
    val message: String,
    val data: List<DaftarVilla>,
)

@Serializable
data class DaftarVillaDetailResponse (
    val status: Boolean,
    val message: String,
    val data: DaftarVilla,
)