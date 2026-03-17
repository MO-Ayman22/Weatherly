package com.example.weatherly.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherly.domain.model.FavoriteWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteWeather)

    @Query("SELECT * FROM favorite_locations WHERE language = :language")
    fun getAllFavorites(language: String): Flow<List<FavoriteWeather>>

    @Query(" DELETE FROM favorite_locations WHERE lat = :lat AND lon = :lon")
    suspend fun deleteFavoriteByLocation(lat: Double, lon: Double)
}