package com.example.weatherly.data.repository

import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.domain.model.WeatherAlert
import com.example.weatherly.domain.repository.AlertRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AlertRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : AlertRepository {
    override fun getAlerts(): Flow<List<WeatherAlert>> {
        return localDataSource.getAlerts()
    }

    override suspend fun insertAlert(alert: WeatherAlert) {
        localDataSource.insertAlert(alert)
    }

    override suspend fun updateLastTriggered(type: String, lastTriggered: Long) {
        localDataSource.updateLastTriggered(type, lastTriggered)
    }
}