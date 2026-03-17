package com.example.weatherly.data.source.preferences

import android.location.Location

interface PreferencesDataSource {
    fun isFirstTime(): Boolean

    fun setFirstTime(isFirst: Boolean)

    fun getSavedLocation(): Pair<Float, Float>

    fun saveLocation(location: Location)
    fun saveLocation(lat: Double, lon: Double)

    fun getLanguage(): String
    suspend fun saveLanguage(langCode: String)

    fun getTempUnit(): String
    suspend fun saveTempUnit(unit: String)

    fun getWindSpeedUnit(): String
    suspend fun saveWindSpeedUnit(unit: String)

    fun getLocationMethod(): String
    fun saveLocationMethod(method: String)

}