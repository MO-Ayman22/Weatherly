package com.example.weatherly.data.repository

import com.example.weatherly.data.mapper.toCurrentWeather
import com.example.weatherly.data.mapper.toFavoriteWeather
import com.example.weatherly.data.source.local.LocalDataSource
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.domain.model.FavoriteWeather
import com.example.weatherly.domain.repository.FavoriteRepository
import com.example.weatherly.utils.AppConstants
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class FavoriteRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : FavoriteRepository {
    override suspend fun insertFavorite(lat: Double, lon: Double) {

        val weather =
            remoteDataSource.getForecast(lat = lat, lon = lon, lang = AppConstants.ENGLISH)
                .toCurrentWeather(AppConstants.ENGLISH)

        val favorite = weather.toFavoriteWeather()

        localDataSource.insertFavorite(favorite)

        val arabicWeather =
            remoteDataSource.getForecast(lat = lat, lon = lon, lang = AppConstants.ARABIC)
                .toCurrentWeather(AppConstants.ARABIC)
        val arabicFavorite = arabicWeather.toFavoriteWeather()

        localDataSource.insertFavorite(arabicFavorite)
    }

    override suspend fun deleteFavoriteByLocation(lat: Double, lon: Double) {
        localDataSource.deleteFavoriteByLocation(lat, lon)
    }

    override fun getAllFavorites(): Flow<List<FavoriteWeather>> {

        return localDataSource.getAllFavorites(preferencesDataSource.getLanguage())
            .onEach { favorites ->

                val currentTime = System.currentTimeMillis()
                val twoHours = 2 * 60 * 60 * 1000

                favorites.forEach { favorite ->

                    val isExpired = currentTime - favorite.lastFetch > twoHours

                    if (isExpired) {
                        refreshWeather(city = favorite.location)
                    }
                }
            }
    }

    private suspend fun refreshWeather(city: String) {

        var weather = remoteDataSource.getForecast(city = city, lang = AppConstants.ENGLISH)
            .toCurrentWeather(AppConstants.ENGLISH)

        var favorite = weather.toFavoriteWeather()

        localDataSource.insertFavorite(favorite)

        weather = remoteDataSource.getForecast(city = city, lang = AppConstants.ARABIC)
            .toCurrentWeather(AppConstants.ARABIC)

        favorite = weather.toFavoriteWeather()

        localDataSource.insertFavorite(favorite)

    }
}