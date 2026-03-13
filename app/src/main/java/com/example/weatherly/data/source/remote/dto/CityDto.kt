package com.example.weatherly.data.source.remote.dto

data class CityDto(
    val id: Int,
    val name: String,
    val coord: CoordDto,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)