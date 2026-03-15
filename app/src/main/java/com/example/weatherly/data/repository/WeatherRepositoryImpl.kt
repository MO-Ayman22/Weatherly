package com.example.weatherly.data.repository

import com.example.weatherly.data.mapper.toCurrentWeather
import com.example.weatherly.data.mapper.toDailyWeather
import com.example.weatherly.data.mapper.toHourlyWeather
import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.domain.repository.WeatherRepository
import com.example.weatherly.utils.AppConstants
import jakarta.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val prefsDataSource: PreferencesDataSource,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {

    override suspend fun refreshWeather(city: String?, lat: Double?, lon: Double?) {
        var lang = prefsDataSource.getLanguage()
        if (lang != AppConstants.ENGLISH && lang != AppConstants.ARABIC)
            lang = AppConstants.ENGLISH
        val tempUnit = prefsDataSource.getTempUnit()
        val windSpeedUnit = prefsDataSource.getWindSpeedUnit()

        val forecastDto = remoteDataSource.getForecast(city, lat, lon, lang = lang)

        forecastDto.let {
            // Mappers
            val current = it.toCurrentWeather(tempUnit, windSpeedUnit)
            val hourly = it.toHourlyWeather(tempUnit)
            val daily = it.toDailyWeather(tempUnit)

            localDataSource.deleteAll()
            localDataSource.insertCurrentWeather(current)
            localDataSource.insertHourlyWeather(hourly)
            localDataSource.insertDailyWeather(daily)
        }
    }

    override suspend fun getCurrentWeather() = localDataSource.getCurrentWeather()

    override suspend fun getHourlyWeather() = localDataSource.getHourlyWeather()

    override suspend fun getDailyWeather() = localDataSource.getDailyWeather()

    override fun isFirstTime(): Boolean {
        return prefsDataSource.isFirstTime()
    }

    override fun setFirstTime(isFirst: Boolean) {
        prefsDataSource.setFirstTime(isFirst)
    }
}