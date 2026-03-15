package com.example.weatherly.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherly.domain.model.WeatherAlert
import kotlinx.coroutines.flow.Flow


@Dao
interface AlertDao {

    @Query("SELECT * FROM alerts Order by alarmType")
    fun getAlerts(): Flow<List<WeatherAlert>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: WeatherAlert)

    @Query("UPDATE alerts SET lastTriggered = :lastTriggered WHERE alarmType = :type")
    suspend fun updateLastTriggered(type: String, lastTriggered: Long)
}