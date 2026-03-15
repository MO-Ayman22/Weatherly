package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.model.WeatherAlert
import com.example.weatherly.domain.repository.AlertRepository
import jakarta.inject.Inject

class InsertAlertUseCase @Inject constructor(
    private val repository: AlertRepository
) {

    suspend operator fun invoke(alert: WeatherAlert) {
        repository.insertAlert(alert)
    }
}