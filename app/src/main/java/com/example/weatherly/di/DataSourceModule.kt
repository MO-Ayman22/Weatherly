package com.example.weatherly.di

import android.content.SharedPreferences
import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.data.source.local.LocalDataSourceImpl
import com.example.weatherly.data.source.local.dao.AlertDao
import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSourceImpl
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.data.source.remote.RemoteDataSourceImpl
import com.example.weatherly.data.source.remote.api.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(
        sharedPreferences: SharedPreferences
    ): PreferencesDataSource {
        return PreferencesDataSourceImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        weatherService: WeatherService
    ): RemoteDataSource {
        return RemoteDataSourceImpl(weatherService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        weatherDao: WeatherDao,
        alertDao: AlertDao
    ): LocalDataSource {
        return LocalDataSourceImpl(weatherDao, alertDao)
    }

}