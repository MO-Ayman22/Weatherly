package com.example.weatherly.data.mapper

fun msToKmh(ms: Double): Double {
    return ms * 3.6
}

fun celsiusToFahrenheit(c: Double): Double {
    return (c * 9 / 5) + 32
}