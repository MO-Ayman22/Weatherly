package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.LocationRepository
import jakarta.inject.Inject

class IsGpsEnabledUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Boolean {
        return locationRepository.isGpsEnabled()
    }
}