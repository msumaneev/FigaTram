package com.figatram.domain.usecase

import com.figatram.data.local.dao.TramDao
import com.figatram.data.local.entity.ArrivalStatsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecordArrivalUseCase(private val tramDao: TramDao) {
    suspend operator fun invoke(vehicleId: String, stationId: String) {
        withContext(Dispatchers.IO) {
            val isTarget = vehicleId == "152" || vehicleId == "175"
            val tramInfo = tramDao.getTramInfo(vehicleId)
            val isFavorite = tramInfo?.isFavorite == true

            if (isTarget || isFavorite) {
                val stat = ArrivalStatsEntity(
                    vehicleId = vehicleId,
                    stationId = stationId,
                    timestampArrived = System.currentTimeMillis()
                )
                tramDao.insertArrivalStat(stat)
            }
        }
    }
}
