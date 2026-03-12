package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.WeatherRepository
import jakarta.inject.Inject

class SetFirstTimeUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(isFirstTime: Boolean) {
        repository.setFirstTime(isFirstTime)
    }
}