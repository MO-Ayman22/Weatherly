package com.example.weatherly.presentation.feature.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.domain.usecase.CheckInternetConnectionUseCase
import com.example.weatherly.domain.usecase.GetCurrentLocationUseCase
import com.example.weatherly.domain.usecase.GetCurrentWeatherUseCase
import com.example.weatherly.domain.usecase.GetDailyWeatherUseCase
import com.example.weatherly.domain.usecase.GetHourlyWeatherUseCase
import com.example.weatherly.domain.usecase.GetSavedLocationUseCase
import com.example.weatherly.domain.usecase.HasLocationPermissionUseCase
import com.example.weatherly.domain.usecase.IsGpsEnabledUseCase
import com.example.weatherly.domain.usecase.RefreshWeatherUseCase
import com.example.weatherly.domain.usecase.SaveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val refreshWeatherUseCase: RefreshWeatherUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getDailyWeatherUseCase: GetDailyWeatherUseCase,
    private val getHourlyWeatherUseCase: GetHourlyWeatherUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val hasLocationPermissionUseCase: HasLocationPermissionUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val isGpsEnabledUseCase: IsGpsEnabledUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState>
        get() = _uiState
    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent: SharedFlow<String>
        get() = _snackbarEvent
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    init {
        loadWeatherOnStart()
    }

    fun loadWeatherOnStart() {
        viewModelScope.launch {
            val location = try {
                getSavedLocationUseCase()
            } catch (_: Exception) {
                null
            }
            val hasInternet = try {
                checkInternetConnectionUseCase()
            } catch (_: Exception) {
                false
            }
            updateLocation()
            try {
                if (hasInternet && location != null) {
                    refreshWeatherUseCase(
                        lat = location.first.toDouble(),
                        lon = location.second.toDouble()
                    )
                }
            } catch (e: Exception) {
                if (e is java.net.SocketTimeoutException) {
                    _snackbarEvent.emit("No internet connection")
                } else {
                    _snackbarEvent.emit("Failed to load weather")
                }
            }

            val current = getCurrentWeatherUseCase()
            val hourly = getHourlyWeatherUseCase()
            val daily = getDailyWeatherUseCase()

            if (current != null) {
                _uiState.value = HomeUiState.Success(
                    current = current,
                    hourly = hourly,
                    daily = daily,
                    date = getCurrentDateTime()
                )
            } else if (!hasInternet) {
                _snackbarEvent.emit("No internet connection and no cached data")
                _uiState.value = HomeUiState.NoConnection
            }
        }
    }

    fun refreshWeatherManually() {
        viewModelScope.launch {
            _isRefreshing.value = true
            updateLocation()
            try {
                val location = getSavedLocationUseCase()
                val hasInternet = checkInternetConnectionUseCase()

                if (!hasInternet) {
                    delay(100)
                    _snackbarEvent.emit("No internet connection")
                    return@launch
                }

                refreshWeatherUseCase(
                    lat = location.first.toDouble(),
                    lon = location.second.toDouble()
                )

                val current = getCurrentWeatherUseCase()
                val hourly = getHourlyWeatherUseCase()
                val daily = getDailyWeatherUseCase()
                if (current == null) {
                    _snackbarEvent.emit("Failed to refresh weather")
                    return@launch
                }

                _uiState.value = HomeUiState.Success(
                    current = current,
                    hourly = hourly,
                    daily = daily,
                    date = getCurrentDateTime()
                )

            } catch (_: Exception) {
                _snackbarEvent.emit("Failed to refresh weather")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private fun updateLocation() {
        viewModelScope.launch {
            try {
                if (hasLocationPermissionUseCase() && isGpsEnabledUseCase()) {
                    val location = getCurrentLocationUseCase()
                    if (location != null) {
                        saveLocationUseCase(location)
                    }
                } else {
                    _snackbarEvent.tryEmit("Access your location to get petter results")
                }

            } catch (_: Exception) {
                _snackbarEvent.emit("Failed to get location")
            }
        }
    }

    fun getCurrentDateTime(): String {
        val formatter = SimpleDateFormat("EEE, dd MMM • HH:mm", Locale.getDefault())
        return formatter.format(Date())
    }
}