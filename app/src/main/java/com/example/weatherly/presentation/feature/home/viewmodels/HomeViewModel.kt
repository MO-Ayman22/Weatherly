package com.example.weatherly.presentation.feature.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.core.NetworkChecker
import com.example.weatherly.domain.repository.LocationRepository
import com.example.weatherly.domain.repository.SettingsRepository
import com.example.weatherly.domain.repository.WeatherRepository
import com.example.weatherly.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val networkChecker: NetworkChecker,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        loadWeatherOnStart()
    }

    fun loadWeatherOnStart() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                updateLocation()
                val location = locationRepository.getSavedLocation()
                if (networkChecker.isConnected() && location != null) {
                    weatherRepository.refreshWeather(
                        lat = location.first.toDouble(),
                        lon = location.second.toDouble()
                    )
                }
            } catch (_: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Failed to update weather data"))
            }

            loadDataFromCache()
        }
    }

    fun refreshWeatherManually() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                updateLocation()
                val location = locationRepository.getSavedLocation()
                val hasInternet = networkChecker.isConnected()

                if (!hasInternet) {
                    _uiEvent.emit(UiEvent.ShowSnackbar("No internet connection available"))
                    return@launch
                }

                if (location != null) {
                    weatherRepository.refreshWeather(
                        lat = location.first.toDouble(),
                        lon = location.second.toDouble()
                    )
                    _uiEvent.emit(UiEvent.ShowSnackbar("Weather updated successfully"))
                    loadDataFromCache()
                } else {
                    _uiEvent.emit(UiEvent.ShowSnackbar("Please select a location first"))
                }

            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Error: Could not refresh weather"))
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private suspend fun loadDataFromCache() {
        val current = weatherRepository.getCurrentWeather()
        val hourly = weatherRepository.getHourlyWeather()
        val daily = weatherRepository.getDailyWeather()

        if (current != null) {
            _uiState.value = HomeUiState.Success(
                current = current,
                hourly = hourly,
                daily = daily,
                date = getCurrentDateTime()
            )
        } else {
            _uiState.value = HomeUiState.NoConnection
        }
    }

    private suspend fun updateLocation() {
        try {
            val isGpsMethod = settingsRepository.getLocationMethod() == AppConstants.GPS_METHOD_KEY
            if (networkChecker.isConnected() && locationRepository.isGpsEnabled() && isGpsMethod) {
                locationRepository.getCurrentLocation()?.let {
                    locationRepository.saveLocation(it)
                }
            }
        } catch (_: Exception) {
            _uiEvent.emit(UiEvent.ShowSnackbar("Unable to fetch your current location"))
        }
    }

    fun getWindSpeedUnit() = settingsRepository.getWindSpeedUnit()
    fun getTempUnit() = settingsRepository.getTempUnit()

    private fun getCurrentDateTime(): String {
        val formatter = SimpleDateFormat("EEE, dd MMM • HH:mm", Locale.getDefault())
        return formatter.format(Date())
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}