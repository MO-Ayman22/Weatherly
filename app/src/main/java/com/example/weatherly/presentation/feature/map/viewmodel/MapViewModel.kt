package com.example.weatherly.presentation.feature.map.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.core.NetworkChecker
import com.example.weatherly.domain.model.CityLocation
import com.example.weatherly.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val checker: NetworkChecker
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var searchResults by mutableStateOf<List<CityLocation>>(emptyList())
    var selectedLocation by mutableStateOf<CityLocation?>(null)
    var isSearching by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var searchJob: Job? = null

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery

        if (!checker.isConnected()) {
            errorMessage = "No internet connection"
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            if (newQuery.isNotBlank()) {
                isSearching = true
                try {
                    searchResults = locationRepository.searchCity(newQuery)
                } catch (e: Exception) {
                    errorMessage = "Search failed"
                } finally {
                    isSearching = false
                }
            } else {
                searchResults = emptyList()
            }
        }
    }

    fun onMapLongPress(lat: Double, lon: Double) {
        if (!checker.isConnected()) {
            errorMessage = "No internet connection"
            return
        }

        viewModelScope.launch {
            try {
                val result = locationRepository.reverseGeocode(lat, lon)
                selectedLocation = result
                searchQuery = result.cityName
            } catch (e: Exception) {
                errorMessage = "Could not fetch city details"
            }
        }
    }

    fun onLocationSelected(location: CityLocation) {
        selectedLocation = location
        searchResults = emptyList()
        searchQuery = location.cityName
    }

    fun errorShown() {
        errorMessage = null
    }
}