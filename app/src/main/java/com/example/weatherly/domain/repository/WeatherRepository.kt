package com.example.weatherly.domain.repository

interface WeatherRepository {
    fun isFirstTime(): Boolean

    fun setFirstTime(isFirst: Boolean)
}