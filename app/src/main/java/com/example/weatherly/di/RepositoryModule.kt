package com.example.weatherly.di

import com.example.weatherly.data.repository.AlertRepositoryImpl
import com.example.weatherly.data.repository.LocationRepositoryImpl
import com.example.weatherly.data.repository.SettingsRepositoryImpl
import com.example.weatherly.data.repository.WeatherRepositoryImpl
import com.example.weatherly.domain.repository.AlertRepository
import com.example.weatherly.domain.repository.LocationRepository
import com.example.weatherly.domain.repository.SettingsRepository
import com.example.weatherly.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    fun provideLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    fun provideAlertRepository(
        alertRepositoryImpl: AlertRepositoryImpl
    ): AlertRepository

    @Binds
    @Singleton
    fun provideSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository


}