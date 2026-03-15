package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.SettingsRepository
import jakarta.inject.Inject

class GetTempUnitUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getTempUnit()
}