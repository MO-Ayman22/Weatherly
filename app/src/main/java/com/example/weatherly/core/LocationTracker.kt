package com.example.weatherly.core

interface LocationTracker {

    suspend fun getCurrentLocation(): android.location.Location?

    fun isGpsEnabled(): Boolean
}