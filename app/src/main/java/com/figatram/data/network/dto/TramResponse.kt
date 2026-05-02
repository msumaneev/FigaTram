package com.figatram.data.network.dto

import com.google.gson.annotations.SerializedName

data class TramResponse(
    @SerializedName("vehicles") val vehicles: List<VehicleDto>
)

data class VehicleDto(
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("position") val position: PositionDto?,
    @SerializedName("stop_id") val stopId: String?
)

data class PositionDto(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
