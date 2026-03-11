package com.example.weatherly.di

import com.example.weatherly.core.LocationTracker
import com.example.weatherly.data.repository.LocationRepositoryImpl
import com.example.weatherly.data.repository.WeatherRepositoryImpl
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.domain.repository.LocationRepository
import com.example.weatherly.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        preferencesDataSource: PreferencesDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl(preferencesDataSource)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        tracker: LocationTracker,
        preferencesDataSource: PreferencesDataSource
    ): LocationRepository {
        return LocationRepositoryImpl(tracker, preferencesDataSource)
    }
}