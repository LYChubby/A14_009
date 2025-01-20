package com.example.villaapps.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pelanggan (

    @SerialName("id_pelanggan")
    val idPelanggan: Int,

    @SerialName("nama_pelanggan")
    val namaPelanggan: String,

    @SerialName("no_hp")
    val noHp: String,

)

@Serializable
data class AllPelangganResponse (
    val status: Boolean,
    val message: String,
    val data: List<Pelanggan>,
)

@Serializable
data class DetailPelangganResponse (
    val status: Boolean,
    val message: String,
    val data: Pelanggan,
)