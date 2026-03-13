package com.example.weatherly.data.repository

import com.example.weatherly.data.mapper.toCurrentWeather
import com.example.weatherly.data.mapper.toDailyWeather
import com.example.weatherly.data.mapper.toHourlyWeather
import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.domain.repository.WeatherRepository
import jakarta.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val prefsDataSource: PreferencesDataSource,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {

    override suspend fun refreshWeather(city: String?, lat: Double?, lon: Double?) {
        val forecastDto = remoteDataSource.getForecast(city, lat, lon)

        forecastDto.let {
            // Mappers
            val current = it.toCurrentWeather()
            val hourly = it.toHourlyWeather()
            val daily = it.toDailyWeather()


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