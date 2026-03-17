package com.example.weatherly.domain.model

import androidx.room.Entity

@Entity(
    tableName = "daily_weather",
    primaryKeys = ["date", "language"]
)
data class DailyWeather(
    val date: String,
    val language: String,
    val day: String,
    val temp: Int,
    val description: String,
    val icon: Int
)