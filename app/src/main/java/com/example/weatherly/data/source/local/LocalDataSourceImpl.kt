package com.example.weatherly.data.source.local

import com.example.weatherly.data.source.local.dao.AlertDao
import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather
import com.example.weatherly.domain.model.WeatherAlert
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl @Inject constructor(
    private val dao: WeatherDao,
    private val alertDao: AlertDao
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

    override fun getAlerts(): Flow<List<WeatherAlert>> {
        return alertDao.getAlerts()
    }

    override suspend fun insertAlert(alert: WeatherAlert) {
        alertDao.insertAlert(alert)
    }

    override suspend fun updateLastTriggered(type: String, lastTriggered: Long) {
        alertDao.updateLastTriggered(type, lastTriggered)
    }
}