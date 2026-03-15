package com.example.weatherly.data.worker

import com.example.weatherly.domain.repository.AlertRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AlarmReceiverEntryPoint {
    fun alertRepository(): AlertRepository
}