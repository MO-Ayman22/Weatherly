package com.example.weatherly.di

import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.data.source.local.LocalDataSourceImpl
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSourceImpl
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun providePreferencesDataSource(
        preferencesDataSourceImpl: PreferencesDataSourceImpl
    ): PreferencesDataSource

    @Binds
    @Singleton
    fun provideRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    fun provideLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource
}