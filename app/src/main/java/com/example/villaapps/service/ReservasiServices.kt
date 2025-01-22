package com.example.villaapps.service

import com.example.villaapps.model.AllPelangganResponse
import com.example.villaapps.model.AllReservasiResponse
import com.example.villaapps.model.DaftarVillaResponse
import com.example.villaapps.model.DetailReservasiResponse
import com.example.villaapps.model.Reservasi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservasiServices {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertReservasi(@Body reservasi: Reservasi)

    @GET(".")
    suspend fun getAllReservasi(): AllReservasiResponse

    @GET("{idReservasi}")
    suspend fun getReservasiById(@Path("idReservasi") idReservasi: Int): DetailReservasiResponse

    @PUT("{idReservasi}")
    suspend fun updateReservasi(@Path("idReservasi") idReservasi: Int, @Body reservasi: Reservasi)

    @DELETE("{idReservasi}")
    suspend fun deleteReservasi(@Path("idReservasi") idReservasi: Int): Response<Void>

    @GET("villa")
    suspend fun getDaftarVilla(): DaftarVillaResponse

    @GET("pelanggan")
    suspend fun getDaftarPelanggan(): AllPelangganResponse

}