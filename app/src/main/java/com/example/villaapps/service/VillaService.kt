package com.example.villaapps.service

import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.DaftarVillaDetailResponse
import com.example.villaapps.model.DaftarVillaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VillaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertVilla(@Body daftarVilla: DaftarVilla)

    @GET(".")
    suspend fun getAllVilla(): DaftarVillaResponse

    @GET("{idVilla}")
    suspend fun getVillaById(@Path("idVilla") idVilla: Int): DaftarVillaDetailResponse

    @PUT("{idVilla}")
    suspend fun updateVilla(@Path("idVilla") idVilla: Int, @Body daftarVilla: DaftarVilla)

    @DELETE("{idVilla}")
    suspend fun deleteVilla(@Path("idVilla") idVilla: Int): Response<Void>


}