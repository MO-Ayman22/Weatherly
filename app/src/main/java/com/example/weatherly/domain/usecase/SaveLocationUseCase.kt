package com.example.weatherly.domain.usecase

import android.location.Location
import com.example.weatherly.domain.repository.LocationRepository
import jakarta.inject.Inject

class SaveLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(location: Location) {
        return locationRepository.saveLocation(location)
    }
}