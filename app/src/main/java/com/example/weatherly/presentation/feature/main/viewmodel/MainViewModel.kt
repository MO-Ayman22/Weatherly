package com.example.weatherly.presentation.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherly.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.ShowOnBoarding)
    val uiState: StateFlow<MainUiState>
        get() = _uiState

    fun checkInitialScreen() {
        if (weatherRepository.isFirstTime()) {
            _uiState.value = MainUiState.ShowOnBoarding
        } else {
            _uiState.value = MainUiState.ShowHome
        }
    }

    fun setFirstTime(isFirstTime: Boolean) {
        weatherRepository.setFirstTime(isFirstTime)
    }

    init {
        checkInitialScreen()
    }
}