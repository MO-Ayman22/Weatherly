package com.example.weatherly.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class WeatherAlert(
    @PrimaryKey
    val alarmType: String,
    var notificationType: String,
    var start: Float,
    var end: Float,
    var min: Float,
    var max: Float,
    var notifyBeforeMinutes: Int,
    var lastTriggered: Long?,
    var isEnabled: Boolean
)