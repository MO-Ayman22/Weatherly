package com.example.weatherly.di

import android.content.Context
import androidx.room.Room
import com.example.weatherly.data.source.local.dao.AlertDao
import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.data.source.local.db.AppDatabase
import com.example.weatherly.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppConstants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: AppDatabase): WeatherDao {
        return db.weatherDao()
    }

    @Provides
    @Singleton
    fun provideAlertDao(db: AppDatabase): AlertDao {
        return db.alertDao()
    }
}