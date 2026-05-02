package com.figatram.ui.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.figatram.data.local.entity.ArrivalStatsEntity
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatsScreen(
    statsFlow: StateFlow<List<ArrivalStatsEntity>>
) {
    val stats by statsFlow.collectAsState()
    
    // Group stats by day
    val groupedStats = stats.groupBy { stat ->
        val date = Date(stat.timestampArrived)
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Статистика прибытий", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            groupedStats.forEach { (dateStr, arrivals) ->
                item {
                    Text(text = dateStr, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }
                items(arrivals) { stat ->
                    val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(stat.timestampArrived))
                    Card(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Трамвай #${stat.vehicleId}")
                            Text(text = "Станция: ${stat.stationId}")
                            Text(text = timeStr)
                        }
                    }
                }
            }
        }
    }
}
