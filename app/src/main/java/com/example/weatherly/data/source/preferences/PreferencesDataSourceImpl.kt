package com.example.weatherly.data.source.preferences

import android.content.SharedPreferences
import android.location.Location
import androidx.core.content.edit
import com.example.weatherly.utils.AppConstants
import jakarta.inject.Inject

class PreferencesDataSourceImpl @Inject constructor(
    private val prefs: SharedPreferences
) : PreferencesDataSource {

    override fun isFirstTime(): Boolean {
        return prefs.getBoolean(AppConstants.FIRST_TIME_KEY, true)
    }

    override fun setFirstTime(isFirst: Boolean) {
        prefs.edit { putBoolean(AppConstants.FIRST_TIME_KEY, isFirst) }
    }

    override fun getSavedLocation(): Pair<Float, Float> {
        val latitude = prefs.getFloat(AppConstants.LOCATION_LATITUDE_KEY,0f)
        val longitude = prefs.getFloat(AppConstants.LOCATION_LONGITUDE_KEY,0f)
        return latitude to longitude
    }

    override fun saveLocation(location: Location) {
        prefs.edit {
            putFloat(AppConstants.LOCATION_LATITUDE_KEY, location.latitude.toFloat())
            putFloat(AppConstants.LOCATION_LONGITUDE_KEY, location.longitude.toFloat())
        }
    }
}