package com.example.weatherly.presentation.feature.settings.viewmodel

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.domain.repository.LocationRepository
import com.example.weatherly.domain.repository.SettingsRepository
import com.example.weatherly.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val lang = settingsRepository.getLanguage()
                val temp = settingsRepository.getTempUnit()
                val wind = settingsRepository.getWindSpeedUnit()
                val locationMethod = settingsRepository.getLocationMethod()
                delay(500)
                uiState = uiState.copy(
                    language = lang,
                    tempUnit = temp,
                    windUnit = wind,
                    locationMethod = locationMethod,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false)
                _uiEvent.emit(UiEvent.ShowSnackbar("Failed to load settings"))
            }
        }
    }

    fun changeLanguage(context: Context, lang: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                settingsRepository.saveLanguage(lang)
                uiState = uiState.copy(language = lang)
                delay(800)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    context.getSystemService(LocaleManager::class.java).applicationLocales =
                        LocaleList.forLanguageTags(lang)
                } else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
                }
                uiState = uiState.copy(isLoading = false)
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false)
                _uiEvent.emit(UiEvent.ShowSnackbar("Error changing language"))
            }
        }
    }

    fun changeTempUnit(unit: String) {
        viewModelScope.launch {
            try {
                settingsRepository.saveTempUnit(unit)
                uiState = uiState.copy(tempUnit = unit)
                _uiEvent.emit(UiEvent.ShowSnackbar("Temperature unit updated"))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Failed to save unit"))
            }
        }
    }

    fun changeWindUnit(unit: String) {
        viewModelScope.launch {
            try {
                settingsRepository.saveWindSpeedUnit(unit)
                uiState = uiState.copy(windUnit = unit)
                _uiEvent.emit(UiEvent.ShowSnackbar("Wind speed unit updated"))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Failed to save unit"))
            }
        }
    }

    fun changeLocationMethod(method: String) {
        viewModelScope.launch {
            try {
                settingsRepository.saveLocationMethod(method)
                uiState = uiState.copy(locationMethod = method)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Error updating location method"))
            }
        }
    }

    fun saveMapLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                locationRepository.saveLocation(lat, lon)
                changeLocationMethod(AppConstants.MAP_METHOD_KEY)
                _uiEvent.emit(UiEvent.ShowSnackbar("Location saved from map"))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Failed to save location"))
            }
        }
    }

    fun isGpsEnabled(): Boolean = locationRepository.isGpsEnabled()
    fun onPermissionResult() = changeLocationMethod(AppConstants.GPS_METHOD_KEY)

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}