package com.example.weatherly.domain.usecase

import android.location.Location
import com.example.weatherly.domain.repository.LocationRepository
import jakarta.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Location? {
        return locationRepository.getCurrentLocation()
    }
}

