package com.figatram.ui.directory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.figatram.data.local.entity.TramInfoEntity
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DirectoryScreen(
    tramsFlow: StateFlow<List<TramInfoEntity>>,
    onToggleFavorite: (String, Boolean) -> Unit
) {
    val trams by tramsFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Справочник трамваев", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(trams) { tram ->
                TramDirectoryItem(tram, onToggleFavorite)
            }
        }
    }
}

@Composable
fun TramDirectoryItem(
    tram: TramInfoEntity,
    onToggleFavorite: (String, Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Photo placeholder
            Surface(
                modifier = Modifier.size(64.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            ) {
                // Image would go here based on tram.photoUri
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Трамвай #${tram.vehicleId}", style = MaterialTheme.typography.titleMedium)
                if (tram.model != null) {
                    Text(text = tram.model, style = MaterialTheme.typography.bodyMedium)
                }
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Checkbox(
                    checked = tram.isFavorite,
                    onCheckedChange = { isChecked ->
                        onToggleFavorite(tram.vehicleId, isChecked)
                    }
                )
                Text("Любимый", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
