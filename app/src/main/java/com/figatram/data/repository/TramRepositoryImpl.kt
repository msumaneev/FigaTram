package com.figatram.data.repository

import com.figatram.data.network.ApiClient
import com.figatram.data.network.TramApi
import com.figatram.data.network.dto.VehicleDto
import com.figatram.domain.repository.TramRepository

class TramRepositoryImpl : TramRepository {
    private val api: TramApi = ApiClient.retrofit.create(TramApi::class.java)

    override suspend fun fetchAllTrams(): List<VehicleDto> {
        return try {
            val response = api.getTrams()
            response.vehicles
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
