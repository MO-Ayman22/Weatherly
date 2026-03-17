package com.example.weatherly.domain.repository

import com.example.weatherly.domain.model.FavoriteWeather
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(lat: Double, lon: Double)
    suspend fun deleteFavoriteByLocation(lat: Double, lon: Double)
    fun getAllFavorites(): Flow<List<FavoriteWeather>>
}