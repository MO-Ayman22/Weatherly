package com.example.weatherly.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather")
data class DailyWeather(
    @PrimaryKey
    val day: String,
    val temp: Double,
    val description: String,
    val icon: String
)