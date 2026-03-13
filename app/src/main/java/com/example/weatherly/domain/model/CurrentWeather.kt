package com.example.weatherly.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeather(
    @PrimaryKey val id: Int = 0,
    val temperature: Double,
    val feelsLike: Double,
    val condition: String,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val clouds: Int
)




