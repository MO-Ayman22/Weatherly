package com.example.weatherly.data.source.remote

import com.example.weatherly.data.source.remote.api.GeoApiService
import com.example.weatherly.data.source.remote.api.WeatherService
import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.data.source.remote.dto.GeoApiResponse
import jakarta.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: WeatherService,
    private val geoApi: GeoApiService
) : RemoteDataSource {

    override suspend fun getForecast(
        city: String?,
        lat: Double?,
        lon: Double?,
        lang: String?
    ): ForecastResponseDto {
        return api.getForecast(city, lat, lon, lang)
    }

    override suspend fun searchCity(city: String): List<GeoApiResponse> {
        return geoApi.searchCity(city)
    }

    override suspend fun reverseGeocode(
        lat: Double,
        lon: Double
    ): List<GeoApiResponse> {
        return geoApi.reverseGeocode(lat, lon)
    }
}