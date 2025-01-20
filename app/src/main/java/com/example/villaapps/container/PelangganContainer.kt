package com.example.villaapps.container

import com.example.villaapps.repository.NetworkPelangganRepository
import com.example.villaapps.repository.PelangganRepository
import com.example.villaapps.service.PelangganService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppPelangganContainer {
    val pelangganRepository: PelangganRepository
}

class PelangganContainer : AppPelangganContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/pelanggan/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pelangganService: PelangganService by lazy {
        retrofit.create(PelangganService::class.java)
    }

    override val pelangganRepository: PelangganRepository by lazy {
        NetworkPelangganRepository(pelangganService)
    }
}