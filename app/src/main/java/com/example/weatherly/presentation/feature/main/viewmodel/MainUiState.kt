package com.example.weatherly.presentation.feature.main.viewmodel

sealed class MainUiState {
    object ShowOnBoarding : MainUiState()
    object ShowHome : MainUiState()
}