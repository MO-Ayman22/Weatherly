package com.example.weatherly.domain.usecase

import com.example.weatherly.domain.repository.SettingsRepository
import jakarta.inject.Inject

class SaveLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(langCode: String) = settingsRepository.saveLanguage(langCode)
}

