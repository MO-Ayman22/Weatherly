package com.example.weatherly.di

import android.content.SharedPreferences
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSourceImpl
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
}