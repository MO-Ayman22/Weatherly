package com.example.weatherly.presentation.feature.settings.viewmodel

import com.example.weatherly.utils.AppConstants

data class SettingsUiState(
    val language: String = AppConstants.ENGLISH,
    val tempUnit: String = AppConstants.METRIC,
    val windUnit: String = AppConstants.M_S,
    var isLoading: Boolean = false
)