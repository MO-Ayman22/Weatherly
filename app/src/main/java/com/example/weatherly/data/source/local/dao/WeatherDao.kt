package com.example.weatherly.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather

@Dao
interface WeatherDao {

    // Current
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeather)

    @Query("SELECT * FROM current_weather LIMIT 1")
    suspend fun getCurrentWeather(): CurrentWeather?

    // Hourly
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(list: List<HourlyWeather>)

    @Query("SELECT * FROM hourly_weather ORDER BY time")
    suspend fun getHourlyWeather(): List<HourlyWeather>

    // Daily
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(list: List<DailyWeather>)

    @Query("SELECT * FROM daily_weather ORDER BY date")
    suspend fun getDailyWeather(): List<DailyWeather>
}