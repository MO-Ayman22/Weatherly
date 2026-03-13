package com.example.weatherly.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather

@Database(
    entities = [CurrentWeather::class, HourlyWeather::class, DailyWeather::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}