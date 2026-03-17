package com.example.weatherly.domain.model

data class CityLocation(
    val cityName: String,
    val state: String?,
    val country: String,
    val lat: Double,
    val lon: Double
)