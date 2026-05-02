package com.figatram.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.figatram.data.network.dto.VehicleDto
import com.figatram.domain.repository.TramRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: TramRepository
) : ViewModel() {

    private val _trams = MutableStateFlow<List<VehicleDto>>(emptyList())
    val trams: StateFlow<List<VehicleDto>> = _trams.asStateFlow()

    private val _selectedTram = MutableStateFlow<VehicleDto?>(null)
    val selectedTram = _selectedTram.asStateFlow()

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (isActive) {
                val newTrams = repository.fetchAllTrams()
                _trams.value = newTrams
                delay(20_000) // Poll every 20 seconds
            }
        }
    }

    fun selectTram(tram: VehicleDto) {
        _selectedTram.value = tram
    }

    fun clearSelectedTram() {
        _selectedTram.value = null
    }
}
