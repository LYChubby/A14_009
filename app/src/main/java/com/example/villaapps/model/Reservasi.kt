package com.example.villaapps.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reservasi (

    @SerialName("id_reservasi")
    val idReservasi: Int,

    @SerialName("id_villa")
    val idVilla: Int,

    @SerialName("id_pelanggan")
    val idPelanggan: Int,

    @SerialName("check_in")
    val checkIn: String,

    @SerialName("check_out")
    val checkOut: String,

    @SerialName("jumlah_kamar")
    val jumlahKamar: Int,
)

@Serializable
data class AllReservasiResponse (
    val status: Boolean,
    val message: String,
    val data: List<Reservasi>,
)

@Serializable
data class DetailReservasiResponse (
    val status: Boolean,
    val message: String,
    val data: Reservasi,
)