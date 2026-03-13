package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.WeatherRepository
import jakarta.inject.Inject

class GetHourlyWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke() = weatherRepository.getHourlyWeather()
}