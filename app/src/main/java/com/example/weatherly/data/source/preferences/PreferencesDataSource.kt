package com.example.weatherly.data.source.preferences

import android.location.Location

interface PreferencesDataSource {
    fun isFirstTime(): Boolean

    fun setFirstTime(isFirst: Boolean)

    fun getSavedLocation(): Pair<Float, Float>

    fun saveLocation(location: Location)
}