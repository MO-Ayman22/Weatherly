package com.example.weatherly.data.repository

import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.domain.repository.SettingsRepository
import jakarta.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : SettingsRepository {
    override fun getLanguage(): String {
        return preferencesDataSource.getLanguage()
    }

    override suspend fun saveLanguage(langCode: String) {
        preferencesDataSource.saveLanguage(langCode)
    }

    override fun getTempUnit(): String {
        return preferencesDataSource.getTempUnit()
    }

    override suspend fun saveTempUnit(unit: String) {
        preferencesDataSource.saveTempUnit(unit)
    }

    override fun getWindSpeedUnit(): String {
        return preferencesDataSource.getWindSpeedUnit()
    }

    override suspend fun saveWindSpeedUnit(unit: String) {
        preferencesDataSource.saveWindSpeedUnit(unit)
    }

    override fun saveLocationMethod(locationMethod: String) {
        preferencesDataSource.saveLocationMethod(locationMethod)
    }

    override fun getLocationMethod(): String {
        return preferencesDataSource.getLocationMethod()
    }
}