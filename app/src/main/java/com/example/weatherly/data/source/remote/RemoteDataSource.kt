package com.example.weatherly.data.source.remote

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.data.source.remote.dto.GeoApiResponse

interface RemoteDataSource {
    suspend fun getForecast(
        city: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        lang: String? = null,
    ): ForecastResponseDto

    suspend fun searchCity(
        city: String,
    ): List<GeoApiResponse>

    suspend fun reverseGeocode(
        lat: Double,
        lon: Double,
    ): List<GeoApiResponse>


}