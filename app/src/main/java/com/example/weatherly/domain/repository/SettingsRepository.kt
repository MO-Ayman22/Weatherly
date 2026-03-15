package com.example.weatherly.domain.repository

interface SettingsRepository {

    fun getLanguage(): String
    suspend fun saveLanguage(langCode: String)

    fun getTempUnit(): String
    suspend fun saveTempUnit(unit: String)

    fun getWindSpeedUnit(): String
    suspend fun saveWindSpeedUnit(unit: String)
}