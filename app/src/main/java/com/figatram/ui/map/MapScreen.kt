package com.figatram.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.figatram.data.T3RouteData
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(
    viewModel: MapViewModel
) {
    val trams by viewModel.trams.collectAsState()
    val selectedTram by viewModel.selectedTram.collectAsState()

    // Centered near Antalya
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(36.89, 30.70), 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false)
    ) {
        // Draw the T3 Route
        Polyline(
            points = T3RouteData.routePolyline,
            color = Color.Blue,
            width = 8f
        )

        // Draw Stops
        T3RouteData.stops.forEach { stop ->
            Marker(
                state = MarkerState(position = stop.location),
                title = stop.name,
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            )
        }

        // Draw Trams
        trams.forEach { tram ->
            val position = tram.position
            if (position != null) {
                val isInclusive = tram.vehicleId == "152" || tram.vehicleId == "175"
                // Smooth marker animation is partially handled by recomposition, 
                // but for true smooth movement we'd need a custom MarkerState with animation.
                // For MVP, recomposition handles the update.
                Marker(
                    state = MarkerState(position = LatLng(position.latitude, position.longitude)),
                    title = "Tram ${tram.vehicleId}",
                    snippet = "Click for details",
                    icon = if (isInclusive) {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    } else {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    },
                    onClick = {
                        viewModel.selectTram(tram)
                        true
                    }
                )
            }
        }
    }

    // Bottom Sheet for selected tram
    selectedTram?.let { tram ->
        TramCardBottomSheet(
            tram = tram,
            onDismiss = { viewModel.clearSelectedTram() }
        )
    }
}
