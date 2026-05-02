package com.figatram.ui.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.figatram.data.network.dto.VehicleDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TramCardBottomSheet(
    tram: VehicleDto,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Трамвай #${tram.vehicleId}",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Текущая остановка: ${tram.stopId ?: "Неизвестно"}")
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Placeholder for Favorite Checkbox and Image
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = false, onCheckedChange = { /* TODO update db */ })
                Text("В избранное (Любимый)")
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Button(onClick = { /* TODO Attach Photo logic */ }) {
                Text("Сфотографировать / Прикрепить фото")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
