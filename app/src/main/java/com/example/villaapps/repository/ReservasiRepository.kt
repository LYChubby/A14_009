package com.example.villaapps.repository

import com.example.villaapps.model.AllReservasiResponse
import com.example.villaapps.model.Reservasi
import com.example.villaapps.service.ReservasiServices

interface ReservasiRepository {

    suspend fun insertReservasi(reservasi: Reservasi)

    suspend fun getAllReservasi(): AllReservasiResponse

    suspend fun updateReservasi(idReservasi: Int, reservasi: Reservasi)

    suspend fun deleteReservasi(idReservasi: Int)

    suspend fun getReservasiById(idReservasi: Int): Reservasi
}

class NetworkReservasiRepository(
    private val reservasiApiService: ReservasiServices
) : ReservasiRepository {

    override suspend fun insertReservasi(reservasi: Reservasi) {
        reservasiApiService.insertReservasi(reservasi)
    }

    override suspend fun updateReservasi(idReservasi: Int, reservasi: Reservasi) {
        reservasiApiService.updateReservasi(idReservasi, reservasi)
    }

    override suspend fun deleteReservasi(idReservasi: Int) {
        try {
            val response = reservasiApiService.deleteReservasi(idReservasi)
            if (!response.isSuccessful) {
                throw Exception("Failed to Delete Reservasi. HTTP Status Code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
                }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllReservasi(): AllReservasiResponse =
        reservasiApiService.getAllReservasi()

    override suspend fun getReservasiById(idReservasi: Int): Reservasi {
        return reservasiApiService.getReservasiById(idReservasi).data
    }
}