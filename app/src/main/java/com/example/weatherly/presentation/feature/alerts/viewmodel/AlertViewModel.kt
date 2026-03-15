package com.example.weatherly.presentation.feature.alerts.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherly.data.worker.WeatherAlertWorker
import com.example.weatherly.domain.model.AlertType
import com.example.weatherly.domain.model.NotificationType
import com.example.weatherly.domain.model.WeatherAlert
import com.example.weatherly.domain.usecase.GetAlertsUseCase
import com.example.weatherly.domain.usecase.InsertAlertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val getAlertsUseCase: GetAlertsUseCase,
    private val insertAlertUseCase: InsertAlertUseCase,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    val alerts: StateFlow<List<WeatherAlert>> = getAlertsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadAlerts()
    }

    fun toggleAlert(alert: WeatherAlert, enabled: Boolean) {
        viewModelScope.launch {
            alert.isEnabled = enabled
            insertAlertUseCase(alert)

            val hasEnabledAlert = alerts.value.any { it.isEnabled }
            if (hasEnabledAlert) startWorker(context) else stopWorker(context)
        }
    }

    fun updateAlert(alert: WeatherAlert) {
        viewModelScope.launch {
            insertAlertUseCase(alert)
            startWorker(context)
        }
    }

    private fun startWorker(context: Context) {
        val request = PeriodicWorkRequestBuilder<WeatherAlertWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(2, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "weather_alert_worker",
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }

    private fun stopWorker(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("weather_alert_worker")
    }

    private fun loadAlerts() {
        viewModelScope.launch {

            val list = getAlertsUseCase()
            if (list.first().isEmpty()) {
                val defaultAlerts = listOf(
                    WeatherAlert(
                        alarmType = AlertType.TEMP.name,
                        start = 10f,
                        end = 27f,
                        min = -10f,
                        max = 60f,
                        notificationType = NotificationType.NOTIFICATION.name,
                        notifyBeforeMinutes = 10,
                        lastTriggered = null,
                        isEnabled = false
                    ),
                    WeatherAlert(
                        alarmType = AlertType.HUMIDITY.name,
                        start = 60f,
                        end = 70f,
                        min = 0f,
                        max = 100f,
                        notificationType = NotificationType.NOTIFICATION.name,
                        notifyBeforeMinutes = 10,
                        lastTriggered = null,
                        isEnabled = false
                    ),
                    WeatherAlert(
                        alarmType = AlertType.PRESSURE.name,
                        min = 950f,
                        max = 1050f,
                        start = 960f,
                        end = 1000f,
                        notificationType = NotificationType.NOTIFICATION.name,
                        notifyBeforeMinutes = 10,
                        lastTriggered = null,
                        isEnabled = false
                    )
                )

                defaultAlerts.forEach {
                    insertAlertUseCase(it)
                }
            }
        }
    }
}

