package com.figatram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.figatram.data.local.entity.ArrivalStatsEntity
import com.figatram.data.local.entity.TramInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TramDao {
    @Insert
    suspend fun insertArrivalStat(stat: ArrivalStatsEntity)

    @Query("SELECT * FROM arrival_stats ORDER BY timestampArrived DESC")
    fun getAllArrivalStats(): Flow<List<ArrivalStatsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTramInfo(tramInfo: TramInfoEntity)

    @Query("SELECT * FROM tram_info WHERE vehicleId = :id")
    suspend fun getTramInfo(id: String): TramInfoEntity?

    @Query("SELECT * FROM tram_info")
    fun getAllTramInfos(): Flow<List<TramInfoEntity>>
    
    @Query("UPDATE tram_info SET isFavorite = :isFavorite WHERE vehicleId = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)
}
