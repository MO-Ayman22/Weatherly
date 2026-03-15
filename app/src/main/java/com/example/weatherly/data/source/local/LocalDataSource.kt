package com.example.weatherly.data.source.local

import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather
import com.example.weatherly.domain.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertCurrentWeather(weather: CurrentWeather)
    suspend fun getCurrentWeather(): CurrentWeather?
    suspend fun insertHourlyWeather(list: List<HourlyWeather>)
    suspend fun getHourlyWeather(): List<HourlyWeather>
    suspend fun insertDailyWeather(list: List<DailyWeather>)
    suspend fun getDailyWeather(): List<DailyWeather>
    fun getAlerts(): Flow<List<WeatherAlert>>
    suspend fun insertAlert(alert: WeatherAlert)
    suspend fun updateLastTriggered(type: String, lastTriggered: Long)
}