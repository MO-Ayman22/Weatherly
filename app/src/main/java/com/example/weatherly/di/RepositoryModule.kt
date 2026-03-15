package com.example.weatherly.di

import com.example.weatherly.core.LocationTracker
import com.example.weatherly.data.repository.AlertRepositoryImpl
import com.example.weatherly.data.repository.LocationRepositoryImpl
import com.example.weatherly.data.repository.WeatherRepositoryImpl
import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.domain.repository.AlertRepository
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
        preferencesDataSource: PreferencesDataSource,
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl(preferencesDataSource, localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        tracker: LocationTracker,
        preferencesDataSource: PreferencesDataSource
    ): LocationRepository {
        return LocationRepositoryImpl(tracker, preferencesDataSource)
    }

    @Provides
    @Singleton
    fun provideAlertRepository(
        localDataSource: LocalDataSource
    ): AlertRepository {
        return AlertRepositoryImpl(localDataSource)
    }

}