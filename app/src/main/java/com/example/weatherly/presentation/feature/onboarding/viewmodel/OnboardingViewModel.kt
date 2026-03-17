package com.example.weatherly.presentation.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState.NeedPermission)
    val locationState: StateFlow<LocationState> = _locationState

    init {
        checkPermissionAndGps()
    }

    fun checkPermissionAndGps() {
        val hasPermission = locationRepository.hasLocationPermission()
        _locationState.value = when {
            !hasPermission -> LocationState.NeedPermission
            !locationRepository.isGpsEnabled() -> LocationState.GpsDisabled
            else -> {
                getLocation()
                LocationState.Granted
            }
        }
    }

    fun onPermissionResult() {
        checkPermissionAndGps()
    }

    fun getLocation() {
        viewModelScope.launch {
            val location = locationRepository.getCurrentLocation()
            location?.let{
                locationRepository.saveLocation(it)
            }
        }
    }
}