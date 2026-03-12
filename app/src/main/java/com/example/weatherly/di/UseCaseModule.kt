package com.example.weatherly.di

import com.example.weatherly.domain.repository.LocationRepository
import com.example.weatherly.domain.repository.WeatherRepository
import com.example.weatherly.domain.usecase.GetCurrentLocationUseCase
import com.example.weatherly.domain.usecase.GetSavedLocationUseCase
import com.example.weatherly.domain.usecase.HasLocationPermissionUseCase
import com.example.weatherly.domain.usecase.IsFirstTimeUseCase
import com.example.weatherly.domain.usecase.IsGpsEnabledUseCase
import com.example.weatherly.domain.usecase.SaveLocationUseCase
import com.example.weatherly.domain.usecase.SetFirstTimeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideIsFirstTimeUseCase(
        repository: WeatherRepository
    ): IsFirstTimeUseCase {
        return IsFirstTimeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetFirstTimeUseCase(
        repository: WeatherRepository
    ): SetFirstTimeUseCase {
        return SetFirstTimeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(
       repository: LocationRepository
    ): GetCurrentLocationUseCase {
        return GetCurrentLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsGpsEnabledUseCase(
        repository: LocationRepository
    ): IsGpsEnabledUseCase {
        return IsGpsEnabledUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSavedLocationUseCase(
        repository: LocationRepository
    ): GetSavedLocationUseCase {
        return GetSavedLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveLocationUseCase(
        repository: LocationRepository
    ): SaveLocationUseCase {
        return SaveLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideHasLocationPermissionUseCase(
        repository: LocationRepository
    ): HasLocationPermissionUseCase {
        return HasLocationPermissionUseCase(repository)
    }
}