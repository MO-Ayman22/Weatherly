package com.example.weatherly.presentation.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherly.domain.usecase.IsFirstTimeUseCase
import com.example.weatherly.domain.usecase.SetFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isFirstTimeUseCase: IsFirstTimeUseCase,
    private val setFirstTimeUseCase: SetFirstTimeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.ShowOnBoarding)
    val uiState: StateFlow<MainUiState>
        get() = _uiState

    fun checkInitialScreen() {
        if (isFirstTimeUseCase()) {
            _uiState.value = MainUiState.ShowOnBoarding
        } else {
            _uiState.value = MainUiState.ShowHome
        }
    }

    fun setFirstTime(isFirstTime: Boolean) {
        setFirstTimeUseCase(isFirstTime)
    }

    init {
        checkInitialScreen()
    }
}