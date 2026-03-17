package com.example.weatherly.data.repository

import android.location.Location
import com.example.weatherly.core.LocationTracker
import com.example.weatherly.data.mapper.toCityLocation
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.domain.model.CityLocation
import com.example.weatherly.domain.repository.LocationRepository
import jakarta.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationTracker: LocationTracker,
    private val preferencesDataSource: PreferencesDataSource,
    private val remoteDataSource: RemoteDataSource
) : LocationRepository {

    override fun hasLocationPermission(): Boolean {
        return locationTracker.hasLocationPermission()
    }

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

    override fun saveLocation(lat: Double, lon: Double) {
        preferencesDataSource.saveLocation(lat, lon)
    }

    override suspend fun searchCity(city: String): List<CityLocation> {
        return remoteDataSource.searchCity(city).map { it.toCityLocation() }
    }

    override suspend fun reverseGeocode(
        lat: Double,
        lon: Double
    ): CityLocation {
        return remoteDataSource.reverseGeocode(lat, lon).first().toCityLocation()
    }

}