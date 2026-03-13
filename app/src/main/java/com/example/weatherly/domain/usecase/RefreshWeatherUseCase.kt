package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.WeatherRepository
import jakarta.inject.Inject

class RefreshWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String? = null, lat: Double? = null, lon: Double? = null) {
        weatherRepository.refreshWeather(city, lat, lon)
    }
}