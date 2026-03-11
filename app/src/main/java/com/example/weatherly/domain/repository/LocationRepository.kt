package com.example.weatherly.domain.repository

import android.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location?

    fun isGpsEnabled(): Boolean

    fun getSavedLocation(): Pair<Float, Float>

    fun saveLocation(location: Location)
}