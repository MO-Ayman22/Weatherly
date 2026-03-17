package com.example.weatherly.presentation.feature.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.domain.model.FavoriteWeather
import com.example.weatherly.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoritesRepository: FavoriteRepository
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val favoritesState: StateFlow<List<FavoriteWeather>> =
        favoritesRepository.getAllFavorites()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(10000),
                initialValue = emptyList()
            )

    fun addNewLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                favoritesRepository.insertFavorite(lat, lon)
                _uiEvent.emit(UiEvent.ShowSnackbar("Location added to favorites"))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Failed to add location"))
            }
        }
    }

    fun deleteLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                favoritesRepository.deleteFavoriteByLocation(lat, lon)
                _uiEvent.emit(UiEvent.ShowSnackbar("Location removed"))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Could not delete location"))
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}