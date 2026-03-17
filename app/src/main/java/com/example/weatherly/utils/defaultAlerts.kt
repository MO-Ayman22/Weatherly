package com.example.weatherly.utils

import com.example.weatherly.domain.model.AlertType
import com.example.weatherly.domain.model.NotificationType
import com.example.weatherly.domain.model.WeatherAlert

val defaultAlerts = listOf(
    WeatherAlert(
        alarmType = AlertType.TEMP.name,
        start = 10f,
        end = 27f,
        min = -10f,
        max = 60f,
        notificationType = NotificationType.NOTIFICATION.name,
        notifyBeforeMinutes = 10,
        lastTriggered = null,
        isEnabled = false
    ),
    WeatherAlert(
        alarmType = AlertType.HUMIDITY.name,
        start = 60f,
        end = 70f,
        min = 0f,
        max = 100f,
        notificationType = NotificationType.NOTIFICATION.name,
        notifyBeforeMinutes = 10,
        lastTriggered = null,
        isEnabled = false
    ),
    WeatherAlert(
        alarmType = AlertType.PRESSURE.name,
        min = 950f,
        max = 1050f,
        start = 960f,
        end = 1000f,
        notificationType = NotificationType.NOTIFICATION.name,
        notifyBeforeMinutes = 10,
        lastTriggered = null,
        isEnabled = false
    )
)