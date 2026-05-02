package com.figatram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arrival_stats")
data class ArrivalStatsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: String,
    val stationId: String,
    val timestampArrived: Long
)
