package com.example.weatherly.data.source.remote.dto

data class ForecastResponseDto(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastItemDto>,
    val city: CityDto
)