package com.example.weatherly.data.repository

import android.location.Location
import com.example.weatherly.core.LocationTracker
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.domain.repository.LocationRepository
import jakarta.inject.Inject

class LocationRepositoryImpl @Inject constructor (
    private val locationTracker: LocationTracker,
    private val preferencesDataSource: PreferencesDataSource
): LocationRepository {
    override suspend fun getCurrentLocation(): Location? {
        return locationTracker.getCurrentLocation()
    }

    override fun isGpsEnabled(): Boolean {
        return locationTracker.isGpsEnabled()
    }

    override fun getSavedLocation(): Pair<Float, Float> {
        return preferencesDataSource.getSavedLocation()
    }

    override fun saveLocation(location: Location) {
        preferencesDataSource.saveLocation(location)
    }
}