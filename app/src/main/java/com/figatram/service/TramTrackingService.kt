package com.figatram.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.figatram.data.T3RouteData
import com.figatram.domain.repository.TramRepository
import com.figatram.data.repository.TramRepositoryImpl
import com.figatram.data.local.dao.TramDao
// import com.figatram.data.local.FigaTramDatabase
// For a real app, inject these dependencies via Hilt or manual DI container
import kotlinx.coroutines.*

class TramTrackingService : Service() {

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)
    private val repository: TramRepository = TramRepositoryImpl()

    // Stub for testing, in a real app these would be retrieved from SharedPreferences/DataStore
    private val targetStopNames = setOf("Müze", "Doğu Garajı")
    private val alertTimeMinutes = 15.0
    private val notifiedTrams = mutableSetOf<String>()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createForegroundNotification())
        
        startTracking()
    }

    private fun startTracking() {
        serviceScope.launch {
            while (isActive) {
                val trams = repository.fetchAllTrams()
                
                // Get target stops locations
                val targetStops = T3RouteData.stops.filter { targetStopNames.contains(it.name) }
                
                trams.forEach { tram ->
                    // For now, simulate checking if it's inclusive or favorite
                    val isInclusive = tram.vehicleId == "152" || tram.vehicleId == "175"
                    // val isFavorite = tramDao.getTramInfo(tram.vehicleId)?.isFavorite == true
                    val isFavorite = false // Stub
                    
                    if (isInclusive || isFavorite) {
                        tram.position?.let { pos ->
                            targetStops.forEach { stop ->
                                val distanceMeters = FloatArray(1)
                                Location.distanceBetween(
                                    pos.latitude, pos.longitude,
                                    stop.location.latitude, stop.location.longitude,
                                    distanceMeters
                                )
                                
                                // Assume speed of 20 km/h = ~5.55 m/s
                                val speedMs = 5.55f
                                val etaSeconds = distanceMeters[0] / speedMs
                                val etaMinutes = etaSeconds / 60
                                
                                val notificationKey = "${tram.vehicleId}_${stop.name}"
                                
                                if (etaMinutes <= alertTimeMinutes && !notifiedTrams.contains(notificationKey)) {
                                    sendPushNotification(tram.vehicleId, stop.name, etaMinutes.toInt())
                                    notifiedTrams.add(notificationKey)
                                }
                            }
                        }
                    }
                }
                delay(20_000)
            }
        }
    }

    private fun sendPushNotification(vehicleId: String, stopName: String, etaMinutes: Int) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "TRAM_ALERTS")
            .setContentTitle("Трамвай #$vehicleId приближается!")
            .setContentText("Прибудет на станцию $stopName примерно через $etaMinutes мин.")
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        
        manager.notify(vehicleId.hashCode(), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "TRAM_ALERTS",
                "Tram Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createForegroundNotification(): Notification {
        return NotificationCompat.Builder(this, "TRAM_ALERTS")
            .setContentTitle("FigaTram")
            .setContentText("Фоновое отслеживание трамваев активно")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}
