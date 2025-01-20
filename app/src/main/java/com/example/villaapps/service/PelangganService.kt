package com.example.villaapps.service

import com.example.villaapps.model.AllPelangganResponse
import com.example.villaapps.model.DetailPelangganResponse
import com.example.villaapps.model.Pelanggan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PelangganService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertPelanggan(@Body pelanggan: Pelanggan)

    @GET(".")
    suspend fun getAllPelanggan(): AllPelangganResponse

    @GET("{idPelanggan}")
    suspend fun getPelangganById(@Path("idPelanggan") idPelanggan: Int): DetailPelangganResponse

    @PUT("{idPelanggan}")
    suspend fun updatePelanggan(@Path("idPelanggan") idPelanggan: Int, @Body pelanggan: Pelanggan)

    @DELETE("{idPelanggan}")
    suspend fun deletePelanggan(@Path("idPelanggan") idPelanggan: Int): Response<Void>

}