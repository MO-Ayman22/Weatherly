package com.example.weatherly.data.source.remote

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.utils.AppConstants

interface RemoteDataSource {
    suspend fun getForecast(
        city: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        lang: String = AppConstants.ENGLISH,
    ): ForecastResponseDto

}