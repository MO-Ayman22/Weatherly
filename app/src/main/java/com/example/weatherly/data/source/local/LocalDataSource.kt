package com.example.weatherly.data.source.local

import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.FavoriteWeather
import com.example.weatherly.domain.model.HourlyWeather
import com.example.weatherly.domain.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    // Weather
    suspend fun insertCurrentWeather(weather: CurrentWeather)
    suspend fun getCurrentWeather(language: String): CurrentWeather?
    suspend fun insertHourlyWeather(list: List<HourlyWeather>)
    suspend fun getHourlyWeather(language: String): List<HourlyWeather>
    suspend fun insertDailyWeather(list: List<DailyWeather>)
    suspend fun getDailyWeather(language: String): List<DailyWeather>
    suspend fun deleteAll()

    //Alerts
    fun getAlerts(): Flow<List<WeatherAlert>>
    suspend fun insertAlert(alert: WeatherAlert)
    suspend fun updateLastTriggered(type: String, lastTriggered: Long)

    //Favorites
    suspend fun insertFavorite(favorite: FavoriteWeather)
    suspend fun deleteFavoriteByLocation(lat: Double, lon: Double)
    fun getAllFavorites(language: String): Flow<List<FavoriteWeather>>
}