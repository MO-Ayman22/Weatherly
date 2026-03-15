package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.model.WeatherAlert
import com.example.weatherly.domain.repository.AlertRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAlertsUseCase @Inject constructor(
    private val repository: AlertRepository
) {

    operator fun invoke(): Flow<List<WeatherAlert>> {
        return repository.getAlerts()
    }
}