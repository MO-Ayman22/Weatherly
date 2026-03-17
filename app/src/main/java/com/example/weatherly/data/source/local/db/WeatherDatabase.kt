package com.example.weatherly.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherly.data.source.local.dao.AlertDao
import com.example.weatherly.data.source.local.dao.FavoriteDao
import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.FavoriteWeather
import com.example.weatherly.domain.model.HourlyWeather
import com.example.weatherly.domain.model.WeatherAlert

@Database(
    entities = [CurrentWeather::class, HourlyWeather::class, DailyWeather::class, WeatherAlert::class, FavoriteWeather::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun alertDao(): AlertDao

    abstract fun favoriteDao(): FavoriteDao
}