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

    @Query("SELECT * FROM current_weather WHERE language = :language LIMIT 1")
    suspend fun getCurrentWeather(language: String): CurrentWeather?

    @Query("DELETE FROM current_weather")
    suspend fun deleteCurrent()

    // Hourly
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(list: List<HourlyWeather>)

    @Query(" SELECT * FROM hourly_weather WHERE language = :language ORDER BY time")
    suspend fun getHourlyWeather(language: String): List<HourlyWeather>

    @Query("DELETE FROM hourly_weather")
    suspend fun deleteHourly()

    // Daily
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(list: List<DailyWeather>)

    @Query(" SELECT * FROM daily_weather WHERE language = :language ORDER BY date")
    suspend fun getDailyWeather(language: String): List<DailyWeather>

    @Query("DELETE FROM daily_weather")
    suspend fun deleteDaily()




}