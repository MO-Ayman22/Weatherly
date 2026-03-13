package com.example.weatherly.data.source.local

import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather

interface LocalDataSource {
    suspend fun insertCurrentWeather(weather: CurrentWeather)
    suspend fun getCurrentWeather(): CurrentWeather?
    suspend fun insertHourlyWeather(list: List<HourlyWeather>)
    suspend fun getHourlyWeather(): List<HourlyWeather>
    suspend fun insertDailyWeather(list: List<DailyWeather>)
    suspend fun getDailyWeather(): List<DailyWeather>
}