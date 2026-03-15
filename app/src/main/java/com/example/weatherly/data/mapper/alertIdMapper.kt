package com.example.weatherly.data.mapper

import com.example.weatherly.domain.model.AlertType

fun alertIdMapper(alertId: Int): String {
    return when (alertId) {
        1 -> AlertType.TEMP.name
        2 -> AlertType.HUMIDITY.name
        else -> AlertType.PRESSURE.name
    }
}

fun alertIdMapper(alertType: String): Int {
    return when (alertType) {
        AlertType.TEMP.name -> 1
        AlertType.HUMIDITY.name -> 2
        else -> 3
    }
}