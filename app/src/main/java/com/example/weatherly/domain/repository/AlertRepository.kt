package com.example.weatherly.domain.repository

import com.example.weatherly.domain.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

interface AlertRepository {

    suspend fun insertAlert(alert: WeatherAlert)

    fun getAlerts(): Flow<List<WeatherAlert>>

    suspend fun updateLastTriggered(type: String, lastTriggered: Long)
}