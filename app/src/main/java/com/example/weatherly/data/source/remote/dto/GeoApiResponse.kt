package com.example.weatherly.data.source.remote.dto

data class GeoApiResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)