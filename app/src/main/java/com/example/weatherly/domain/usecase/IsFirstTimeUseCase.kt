package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.WeatherRepository
import jakarta.inject.Inject

class IsFirstTimeUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(): Boolean {
        return repository.isFirstTime()
    }
}

