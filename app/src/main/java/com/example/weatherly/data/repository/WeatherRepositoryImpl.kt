package com.example.weatherly.data.repository

import com.example.weatherly.data.mapper.toCurrentWeather
import com.example.weatherly.data.mapper.toDailyWeather
import com.example.weatherly.data.mapper.toHourlyWeather
import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather
import com.example.weatherly.domain.repository.WeatherRepository
import com.example.weatherly.utils.AppConstants
import com.example.weatherly.utils.convertTemp
import com.example.weatherly.utils.convertWind
import jakarta.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val prefsDataSource: PreferencesDataSource,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {

    override suspend fun refreshWeather(city: String?, lat: Double?, lon: Double?) {

        var lang = AppConstants.ENGLISH
        var forecastDto = remoteDataSource.getForecast(city, lat, lon, lang = lang)

        forecastDto.let {

            localDataSource.deleteAll()

            // Mappers
            val current = it.toCurrentWeather(lang)
            val hourly = it.toHourlyWeather(lang)
            val daily = it.toDailyWeather(lang)

            localDataSource.insertCurrentWeather(current)
            localDataSource.insertHourlyWeather(hourly)
            localDataSource.insertDailyWeather(daily)
        }

        lang = AppConstants.ARABIC
        forecastDto = remoteDataSource.getForecast(city, lat, lon, lang = lang)

        forecastDto.let {
            // Mappers
            val current = it.toCurrentWeather(lang)
            val hourly = it.toHourlyWeather(lang)
            val daily = it.toDailyWeather(lang)

            localDataSource.insertCurrentWeather(current)
            localDataSource.insertHourlyWeather(hourly)
            localDataSource.insertDailyWeather(daily)
        }
    }

    override suspend fun getCurrentWeather(): CurrentWeather? {

        val weather = localDataSource.getCurrentWeather(prefsDataSource.getLanguage())

        val tempUnit = prefsDataSource.getTempUnit()
        val windUnit = prefsDataSource.getWindSpeedUnit()

        return weather?.copy(
            temperature = convertTemp(weather.temperature, tempUnit),
            feelsLike = convertTemp(weather.feelsLike, tempUnit),
            windSpeed = convertWind(weather.windSpeed, windUnit).toInt()
        )
    }

    override suspend fun getHourlyWeather(): List<HourlyWeather> {

        val hourly = localDataSource.getHourlyWeather(
            prefsDataSource.getLanguage()
        )

        return hourly.map {
            it.copy(
                temp = convertTemp(it.temp, prefsDataSource.getTempUnit())
            )
        }
    }

    override suspend fun getDailyWeather(): List<DailyWeather> {
        val daily = localDataSource.getDailyWeather(prefsDataSource.getLanguage())
        return daily.map {
            it.copy(
                temp = convertTemp(it.temp, prefsDataSource.getTempUnit())
            )
        }
    }

    override fun isFirstTime(): Boolean {
        return prefsDataSource.isFirstTime()
    }

    override fun setFirstTime(isFirst: Boolean) {
        prefsDataSource.setFirstTime(isFirst)
    }
}