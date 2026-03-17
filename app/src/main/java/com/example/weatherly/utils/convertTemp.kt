package com.example.weatherly.utils

fun convertTemp(temp: Int, unit: String): Int {
    return if (unit == AppConstants.FAHRENHEIT) {
        ((temp * 9 / 5) + 32)
    } else {
        temp
    }
}