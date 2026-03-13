package com.example.weatherly.data.source.remote

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto

interface RemoteDataSource {
    suspend fun getForecast(
        city: String? = null,
        lat: Double? = null,
        lon: Double? = null
    ): ForecastResponseDto

}