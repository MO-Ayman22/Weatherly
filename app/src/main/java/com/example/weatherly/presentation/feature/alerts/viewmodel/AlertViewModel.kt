package com.example.weatherly.presentation.feature.alerts.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherly.data.worker.WeatherAlertWorker
import com.example.weatherly.domain.model.WeatherAlert
import com.example.weatherly.domain.repository.AlertRepository
import com.example.weatherly.utils.AppConstants
import com.example.weatherly.utils.defaultAlerts
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val alertRepository: AlertRepository,
    @param:ApplicationContext private val context: Context
) : ViewModel() {


    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val alerts: StateFlow<List<WeatherAlert>> = alertRepository.getAlerts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadAlerts()
    }

    fun toggleAlert(alert: WeatherAlert, enabled: Boolean) {
        viewModelScope.launch {
            try {
                alert.isEnabled = enabled
                alertRepository.insertAlert(alert)

                val hasEnabledAlert = alerts.value.any { it.isEnabled }
                if (hasEnabledAlert) startWorker(context) else stopWorker(context)

                val message = if (enabled) "Alert enabled" else "Alert disabled"
                _uiEvent.emit(UiEvent.ShowSnackbar(message))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Failed to update alert: ${e.localizedMessage}"))
            }
        }
    }

    fun updateAlert(alert: WeatherAlert) {
        viewModelScope.launch {
            try {
                alertRepository.insertAlert(alert)
                startWorker(context)
                _uiEvent.emit(UiEvent.ShowSnackbar("Settings saved successfully!"))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Error saving settings"))
            }
        }
    }

    private fun startWorker(context: Context) {
        try {
            val request = PeriodicWorkRequestBuilder<WeatherAlertWorker>(60, TimeUnit.MINUTES)
                .setInitialDelay(2, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                AppConstants.WORKER_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                request
            )
        } catch (e: Exception) {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.ShowSnackbar("WorkManager Error"))
            }
        }
    }

    private fun stopWorker(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(AppConstants.WORKER_NAME)
    }

    private fun loadAlerts() {
        viewModelScope.launch {
            try {
                val list = alertRepository.getAlerts().first()
                if (list.isEmpty()) {
                    defaultAlerts.forEach {
                        alertRepository.insertAlert(it)
                    }
                }
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Error loading alerts"))
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}

