package com.example.villaapps.repository

import com.example.villaapps.model.AllPelangganResponse
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.service.PelangganService

interface PelangganRepository {

    suspend fun insertPelanggan(pelanggan: Pelanggan)

    suspend fun updatePelanggan(idPelanggan: Int, pelanggan: Pelanggan)

    suspend fun deletePelanggan(idPelanggan: Int)

    suspend fun getAllPelanggan(): AllPelangganResponse

    suspend fun getPelangganById(idPelanggan: Int): Pelanggan
}

class NetworkPelangganRepository (
    private val pelangganApiService: PelangganService
) : PelangganRepository {

    override suspend fun insertPelanggan(pelanggan: Pelanggan) {
        pelangganApiService.insertPelanggan(pelanggan)
    }

    override suspend fun updatePelanggan(idPelanggan: Int, pelanggan: Pelanggan) {
        pelangganApiService.updatePelanggan(idPelanggan, pelanggan)
    }

    override suspend fun deletePelanggan(idPelanggan: Int) {
        try {
            val response = pelangganApiService.deletePelanggan(idPelanggan)
            if (!response.isSuccessful) {
                throw Exception("Failed to Delete Pelanggan. HTTP Status Code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllPelanggan(): AllPelangganResponse =
        pelangganApiService.getAllPelanggan()

    override suspend fun getPelangganById(idPelanggan: Int): Pelanggan {
        return pelangganApiService.getPelangganById(idPelanggan).data
    }

}