package com.example.weatherly.di

import android.content.Context
import android.location.LocationManager
import com.example.weatherly.core.LocationTracker
import com.example.weatherly.core.LocationTrackerImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationManager(
        @ApplicationContext context: Context
    ): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @Singleton
    fun provideLocationTracker(
        fusedClient: FusedLocationProviderClient,
        locationManager: LocationManager,
        @ApplicationContext context: Context
    ): LocationTracker {
        return LocationTrackerImpl(fusedClient, locationManager,context)
    }
}