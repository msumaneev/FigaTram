package com.figatram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tram_info")
data class TramInfoEntity(
    @PrimaryKey val vehicleId: String,
    val model: String?,
    val description: String?,
    val photoUri: String?,
    val isFavorite: Boolean = false
)
