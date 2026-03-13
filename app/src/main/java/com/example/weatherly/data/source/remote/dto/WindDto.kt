package com.example.weatherly.data.source.remote.dto

data class WindDto(
    val speed: Double,
    val deg: Int,
    val gust: Double?
)