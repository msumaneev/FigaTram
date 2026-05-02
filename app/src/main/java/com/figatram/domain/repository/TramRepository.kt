package com.figatram.domain.repository

import com.figatram.data.network.dto.VehicleDto

interface TramRepository {
    suspend fun fetchAllTrams(): List<VehicleDto>
}
