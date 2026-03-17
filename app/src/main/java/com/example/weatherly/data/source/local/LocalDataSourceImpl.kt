package com.example.weatherly.data.source.local

import com.example.weatherly.data.source.local.dao.AlertDao
import com.example.weatherly.data.source.local.dao.FavoriteDao
import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.FavoriteWeather
import com.example.weatherly.domain.model.HourlyWeather
import com.example.weatherly.domain.model.WeatherAlert
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl @Inject constructor(
    private val dao: WeatherDao,
    private val alertDao: AlertDao,
    private val favoriteDao: FavoriteDao
) : LocalDataSource {
    // Weather
    override suspend fun insertCurrentWeather(weather: CurrentWeather) {
        dao.insertCurrentWeather(weather)
    }

    override suspend fun getCurrentWeather(language: String): CurrentWeather? {
        return dao.getCurrentWeather(language)
    }

    override suspend fun deleteAll() {
        dao.deleteCurrent()
        dao.deleteHourly()
        dao.deleteDaily()
    }

    override suspend fun insertHourlyWeather(list: List<HourlyWeather>) {
        dao.insertHourlyWeather(list)
    }

    override suspend fun getHourlyWeather(language: String): List<HourlyWeather> {
        return dao.getHourlyWeather(language)
    }

    override suspend fun insertDailyWeather(list: List<DailyWeather>) {
        dao.insertDailyWeather(list)
    }

    override suspend fun getDailyWeather(language: String): List<DailyWeather> {
        return dao.getDailyWeather(language)
    }

    // Alerts
    override fun getAlerts(): Flow<List<WeatherAlert>> {
        return alertDao.getAlerts()
    }

    override suspend fun insertAlert(alert: WeatherAlert) {
        alertDao.insertAlert(alert)
    }

    override suspend fun updateLastTriggered(type: String, lastTriggered: Long) {
        alertDao.updateLastTriggered(type, lastTriggered)
    }

    // Favorites
    override suspend fun insertFavorite(favorite: FavoriteWeather) {
        favoriteDao.insertFavorite(favorite)
    }

    override fun getAllFavorites(language: String): Flow<List<FavoriteWeather>> {
        return favoriteDao.getAllFavorites(language)
    }


    override suspend fun deleteFavoriteByLocation(lat: Double, lon: Double) {
        favoriteDao.deleteFavoriteByLocation(lat, lon)
    }
}