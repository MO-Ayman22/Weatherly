package com.example.weatherly.presentation.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.domain.usecase.GetCurrentLocationUseCase
import com.example.weatherly.domain.usecase.HasLocationPermissionUseCase
import com.example.weatherly.domain.usecase.IsGpsEnabledUseCase
import com.example.weatherly.domain.usecase.SaveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val isGpsEnabledUseCase: IsGpsEnabledUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val hasLocationPermissionUseCase: HasLocationPermissionUseCase
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState.NeedPermission)
    val locationState: StateFlow<LocationState> = _locationState

    init {
        checkPermissionAndGps()
    }

    fun checkPermissionAndGps() {
        val hasPermission = hasLocationPermissionUseCase()
        _locationState.value = when {
            !hasPermission -> LocationState.NeedPermission
            !isGpsEnabledUseCase() -> LocationState.GpsDisabled
            else -> {
                getLocation()
                LocationState.Granted
            }
        }
    }

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            checkPermissionAndGps()
        } else {
            _locationState.value = LocationState.NeedPermission
        }
    }

    fun getLocation() {
        viewModelScope.launch {
            val location = getCurrentLocationUseCase()
            location?.let{
                saveLocationUseCase(it)
            }
        }
    }
}