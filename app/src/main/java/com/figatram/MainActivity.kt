package com.figatram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier

import com.figatram.data.repository.TramRepositoryImpl
import com.figatram.ui.map.MapScreen
import com.figatram.ui.map.MapViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                                @Suppress("UNCHECKED_CAST")
                                return MapViewModel(TramRepositoryImpl()) as T
                            }
                            throw IllegalArgumentException("Unknown ViewModel class")
                        }
                    }
                    val mapViewModel: MapViewModel = viewModel(factory = factory)
                    
                    MapScreen(viewModel = mapViewModel)
                }
            }
        }
    }
}
