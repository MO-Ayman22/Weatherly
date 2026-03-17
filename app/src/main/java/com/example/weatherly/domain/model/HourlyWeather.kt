package com.example.weatherly.domain.model

import androidx.room.Entity

@Entity(
    tableName = "hourly_weather",
    primaryKeys = ["time", "language"]
)
data class HourlyWeather(
    val time: String,
    val language: String,
    val temp: Int,
    val condition: String,
    val icon: Int
)