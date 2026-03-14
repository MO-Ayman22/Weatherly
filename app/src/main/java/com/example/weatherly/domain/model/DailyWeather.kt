package com.example.weatherly.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather")
data class DailyWeather(
    @PrimaryKey
    val day: String,
    val date: String,
    val temp: Int,
    val description: String,
    val icon: Int
)