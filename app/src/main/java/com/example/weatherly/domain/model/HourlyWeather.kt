package com.example.weatherly.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_weather")
data class HourlyWeather(
    @PrimaryKey
    val time: String,
    val temp: Int,
    val condition: String,
    val icon: Int
)