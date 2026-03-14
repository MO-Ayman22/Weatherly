package com.example.weatherly.presentation.feature.home.viewmodels

import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather

sealed interface HomeUiState {

    object Loading : HomeUiState

    object NoConnection : HomeUiState

    data class Success(
        val current: CurrentWeather,
        val hourly: List<HourlyWeather>,
        val daily: List<DailyWeather>,
        val date: String
    ) : HomeUiState
}