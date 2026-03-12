package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.LocationRepository
import jakarta.inject.Inject

class GetSavedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Pair<Float, Float> {
        return locationRepository.getSavedLocation()
    }
}