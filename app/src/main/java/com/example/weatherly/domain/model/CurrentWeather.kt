package com.example.weatherly.domain.model

import androidx.room.Entity

@Entity(
    tableName = "current_weather",
    primaryKeys = ["location", "language"]
)
data class CurrentWeather(
    val location: String,
    val language: String,
    val temperature: Int,
    val feelsLike: Int,
    val condition: String,
    val description: String,
    val icon: Int,
    val humidity: Int,
    val windSpeed: Int,
    val pressure: Int,
    val clouds: Int,
    val lat: Double,
    val lon: Double
)




