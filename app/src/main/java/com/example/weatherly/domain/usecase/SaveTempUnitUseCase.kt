package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.SettingsRepository
import jakarta.inject.Inject

class SaveTempUnitUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(tempUnit: String) = settingsRepository.saveTempUnit(tempUnit)
}