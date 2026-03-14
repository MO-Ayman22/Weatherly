package com.example.weatherly.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeather(
    @PrimaryKey val id: Int = 0,
    val location: String,
    val temperature: Int,
    val feelsLike: Int,
    val condition: String,
    val description: String,
    val icon: Int,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val clouds: Int
)




