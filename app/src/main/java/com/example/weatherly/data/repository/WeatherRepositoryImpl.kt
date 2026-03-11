package com.example.weatherly.data.repository

import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.domain.repository.WeatherRepository
import jakarta.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val prefsDataSource: PreferencesDataSource
) : WeatherRepository {

    override fun isFirstTime(): Boolean {
        return prefsDataSource.isFirstTime()
    }

    override fun setFirstTime(isFirst: Boolean) {
        prefsDataSource.setFirstTime(isFirst)
    }

}