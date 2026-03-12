package com.example.weatherly.domain.repository

import android.location.Location

interface LocationRepository {
    fun hasLocationPermission(): Boolean
    suspend fun getCurrentLocation(): Location?

    fun isGpsEnabled(): Boolean

    fun getSavedLocation(): Pair<Float, Float>

    fun saveLocation(location: Location)
}