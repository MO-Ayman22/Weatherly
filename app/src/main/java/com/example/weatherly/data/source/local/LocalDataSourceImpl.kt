package com.example.weatherly.data.source.local

import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather
import jakarta.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: WeatherDao
) : LocalDataSource {

    override suspend fun insertCurrentWeather(weather: CurrentWeather) {
        dao.insertCurrentWeather(weather)
    }

    override suspend fun getCurrentWeather(): CurrentWeather? {
        return dao.getCurrentWeather()
    }

    override suspend fun insertHourlyWeather(list: List<HourlyWeather>) {
        dao.insertHourlyWeather(list)
    }

    override suspend fun getHourlyWeather(): List<HourlyWeather> {
        return dao.getHourlyWeather()
    }

    override suspend fun insertDailyWeather(list: List<DailyWeather>) {
        dao.insertDailyWeather(list)
    }

    override suspend fun getDailyWeather(): List<DailyWeather> {
        return dao.getDailyWeather()
    }
}