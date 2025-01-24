package com.example.villaapps

import android.app.Application
import android.util.Log
import com.example.villaapps.container.AppPelangganContainer
import com.example.villaapps.container.AppReservasiContainer
import com.example.villaapps.container.AppReviewContainer
import com.example.villaapps.container.AppVillaContainer
import com.example.villaapps.container.PelangganContainer
import com.example.villaapps.container.ReservasiContainer
import com.example.villaapps.container.ReviewContainer
import com.example.villaapps.container.VillaContainer
import com.example.villaapps.repository.NetworkDaftarVillaRepository
import com.example.villaapps.repository.NetworkPelangganRepository
import com.example.villaapps.service.PelangganService
import com.example.villaapps.service.VillaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class VillaApplications : Application() {
    lateinit var villacontainer: AppVillaContainer
    lateinit var pelanggancontainer: AppPelangganContainer
    lateinit var reservasicontainer: AppReservasiContainer
    lateinit var reviewcontainer: AppReviewContainer

    override fun onCreate() {
        super.onCreate()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/reservasi/") // Ganti URL Anda
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request()
                Log.d("API Request", "URL: ${request.url}")
                val response = chain.proceed(request)
                Log.d("API Response", "Response: ${response.body?.string()}")
                response
            }.build())
            .build()

        val villaService: VillaService = retrofit.create(VillaService::class.java)
        val pelangganService: PelangganService = retrofit.create(PelangganService::class.java)

        // Contoh instansiasi repository (ubah sesuai implementasi Anda)
        val daftarVillaRepository = NetworkDaftarVillaRepository(daftarVillaApiService = villaService)
        val pelangganRepository = NetworkPelangganRepository(pelangganApiService = pelangganService)

        // Inisialisasi container dengan repository yang sesuai
        villacontainer = VillaContainer()
        pelanggancontainer = PelangganContainer()
        reservasicontainer = ReservasiContainer(
            pelangganRepository = pelangganRepository,
            daftarVillaRepository = daftarVillaRepository
        )
        reviewcontainer = ReviewContainer()
    }
}