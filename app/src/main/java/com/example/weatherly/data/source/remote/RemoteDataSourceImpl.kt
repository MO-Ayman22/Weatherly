package com.example.weatherly.data.source.remote

import com.example.weatherly.data.source.remote.api.WeatherService
import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import jakarta.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: WeatherService
) : RemoteDataSource {

    override suspend fun getForecast(
        city: String?,
        lat: Double?,
        lon: Double?
    ): ForecastResponseDto {
        return api.getForecast(city, lat, lon)
    }
}