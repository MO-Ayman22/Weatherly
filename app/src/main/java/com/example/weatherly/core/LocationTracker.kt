package com.example.weatherly.core

interface LocationTracker {

    fun hasLocationPermission(): Boolean

    suspend fun getCurrentLocation(): android.location.Location?

    fun isGpsEnabled(): Boolean
}