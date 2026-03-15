package com.example.weatherly.presentation.feature.settings.viewmodel

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.domain.usecase.GetLanguageUseCase
import com.example.weatherly.domain.usecase.GetTempUnitUseCase
import com.example.weatherly.domain.usecase.GetWindSpeedUnitUseCase
import com.example.weatherly.domain.usecase.SaveLanguageUseCase
import com.example.weatherly.domain.usecase.SaveTempUnitUseCase
import com.example.weatherly.domain.usecase.SaveWindSpeedUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val getTempUnitUseCase: GetTempUnitUseCase,
    private val saveTempUnitUseCase: SaveTempUnitUseCase,
    private val getWindSpeedUnitUseCase: GetWindSpeedUnitUseCase,
    private val saveWindSpeedUnitUseCase: SaveWindSpeedUnitUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val lang = getLanguageUseCase()
            val temp = getTempUnitUseCase()
            val wind = getWindSpeedUnitUseCase()
            delay(1000)
            uiState = uiState.copy(
                language = lang,
                tempUnit = temp,
                windUnit = wind,
                isLoading = false
            )
        }
    }

    fun changeLanguage(context: Context, lang: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            saveLanguageUseCase(lang)
            uiState = uiState.copy(language = lang)
            delay(1000)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java).applicationLocales =
                    LocaleList.forLanguageTags(lang)
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
            }
            uiState = uiState.copy(isLoading = false)
        }

    }

    fun changeTempUnit(unit: String) {
        viewModelScope.launch {
            saveTempUnitUseCase(unit)
            uiState = uiState.copy(tempUnit = unit)
        }
    }

    fun changeWindUnit(unit: String) {
        viewModelScope.launch {
            saveWindSpeedUnitUseCase(unit)
            uiState = uiState.copy(windUnit = unit)
        }
    }
}