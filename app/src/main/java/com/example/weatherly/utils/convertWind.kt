package com.example.weatherly.utils

fun convertWind(speed: Int, unit: String): Double {
    return (if (unit == AppConstants.KM_H) {
        speed.toDouble() * 3.6
    } else {
        speed.toDouble()
    })
}