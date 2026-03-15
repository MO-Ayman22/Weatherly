package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.SettingsRepository
import jakarta.inject.Inject

class SaveWindSpeedUnitUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(unit: String) = settingsRepository.saveWindSpeedUnit(unit)
}