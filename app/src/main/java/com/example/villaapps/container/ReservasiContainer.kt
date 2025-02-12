package com.example.villaapps.container

import com.example.villaapps.repository.DaftarVillaRepository
import com.example.villaapps.repository.NetworkReservasiRepository
import com.example.villaapps.repository.NetworkReviewRepository
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.repository.ReservasiRepository
import com.example.villaapps.repository.ReviewRepository
import com.example.villaapps.service.ReservasiServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppReservasiContainer {
    val reservasiRepository: ReservasiRepository
    val pelangganRepository: PelangganRepository
    val daftarVillaRepository: DaftarVillaRepository
    val reviewRepository: ReviewRepository
}

class ReservasiContainer(
    override val pelangganRepository: PelangganRepository,
    override val daftarVillaRepository: DaftarVillaRepository,
    override val reviewRepository: NetworkReviewRepository
) : AppReservasiContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/reservasi/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val reservasiService: ReservasiServices by lazy {
        retrofit.create(ReservasiServices::class.java)
    }

    override val reservasiRepository: ReservasiRepository by lazy {
        NetworkReservasiRepository(reservasiService)
    }


}