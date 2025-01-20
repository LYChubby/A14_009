package com.example.villaapps.container

import com.example.villaapps.repository.DaftarVillaRepository
import com.example.villaapps.repository.NetworkDaftarVillaRepository
import com.example.villaapps.service.VillaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppVillaContainer {
    val daftarVillaRepository: DaftarVillaRepository
}

class VillaContainer : AppVillaContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/villa/"
    private val json = Json { ignoreUnknownKeys = true  }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val daftarVillaService: VillaService by lazy {
        retrofit.create(VillaService::class.java)
    }

    override val daftarVillaRepository: DaftarVillaRepository by lazy {
        NetworkDaftarVillaRepository(daftarVillaService)
    }
}