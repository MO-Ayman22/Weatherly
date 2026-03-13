package com.example.weatherly.data.source.remote.api

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String? = null,
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null,
        @Query("cnt") count: Int = 24,
    ): ForecastResponseDto
}