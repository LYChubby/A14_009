package com.example.villaapps.repository

import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.DaftarVillaResponse
import com.example.villaapps.service.VillaService

interface DaftarVillaRepository {

    suspend fun insertVilla(daftarVilla: DaftarVilla)

    suspend fun getAllVilla(): DaftarVillaResponse

    suspend fun updateVilla(idVilla: Int, daftarVilla: DaftarVilla)

    suspend fun deleteVilla(idVilla: Int)

    suspend fun getVillaById(idVilla: Int): DaftarVilla
}

class NetworkDaftarVillaRepository(
    private val daftarVillaApiService: VillaService
) : DaftarVillaRepository {

    override suspend fun insertVilla(daftarVilla: DaftarVilla) {
        daftarVillaApiService.insertVilla(daftarVilla)
    }

    override suspend fun updateVilla(idVilla: Int, daftarVilla: DaftarVilla) {
        daftarVillaApiService.updateVilla(idVilla, daftarVilla)
    }

    override suspend fun deleteVilla(idVilla: Int) {
        try {
            val response = daftarVillaApiService.deleteVilla(idVilla)
            if (!response.isSuccessful) {
                throw Exception("Failed to Delete Villa. HTTP Status Code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllVilla(): DaftarVillaResponse =
        daftarVillaApiService.getAllVilla()

    override suspend fun getVillaById(idVilla: Int): DaftarVilla {
        return daftarVillaApiService.getVillaById(idVilla).data
    }
}