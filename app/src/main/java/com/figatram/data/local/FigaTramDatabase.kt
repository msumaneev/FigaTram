package com.figatram.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.figatram.data.local.dao.TramDao
import com.figatram.data.local.entity.ArrivalStatsEntity
import com.figatram.data.local.entity.TramInfoEntity

@Database(entities = [ArrivalStatsEntity::class, TramInfoEntity::class], version = 1, exportSchema = false)
abstract class FigaTramDatabase : RoomDatabase() {
    abstract fun tramDao(): TramDao
}
