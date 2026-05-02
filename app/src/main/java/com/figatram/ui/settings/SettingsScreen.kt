package com.figatram.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.figatram.data.T3RouteData

@Composable
fun SettingsScreen() {
    // In a real app, these would be backed by DataStore/SharedPreferences and a ViewModel
    var notificationTime by remember { mutableStateOf("15") }
    var selectedStops by remember { mutableStateOf(setOf<String>()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Настройки", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Время оповещения (минут до прибытия):", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = notificationTime,
            onValueChange = { notificationTime = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Целевые станции (остановки посадки):", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        
        // Simulating a multi-select list for stops
        Column {
            T3RouteData.stops.forEach { stop ->
                val isSelected = selectedStops.contains(stop.name)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { checked ->
                            selectedStops = if (checked) {
                                selectedStops + stop.name
                            } else {
                                selectedStops - stop.name
                            }
                        }
                    )
                    Text(stop.name)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /* TODO Save to DataStore */ }, modifier = Modifier.align(Alignment.End)) {
            Text("Сохранить")
        }
    }
}
