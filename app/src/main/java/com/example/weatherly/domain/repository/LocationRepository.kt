package com.example.weatherly.domain.repository

import android.location.Location
import com.example.weatherly.domain.model.CityLocation

interface LocationRepository {
    fun hasLocationPermission(): Boolean
    suspend fun getCurrentLocation(): Location?

    fun isGpsEnabled(): Boolean

    fun getSavedLocation(): Pair<Float, Float>

    fun saveLocation(location: Location)

    fun saveLocation(lat: Double, lon: Double)

    suspend fun searchCity(city: String): List<CityLocation>

    suspend fun reverseGeocode(lat: Double, lon: Double): CityLocation
}