package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.domain.model.CurrentWeather

fun ForecastResponseDto.toCurrentWeather(): CurrentWeather {
    val current = list.first()

    return CurrentWeather(
        temperature = current.main.temp,
        feelsLike = current.main.feels_like,
        condition = current.weather.firstOrNull()?.main ?: "",
        description = current.weather.firstOrNull()?.description ?: "",
        icon = current.weather.firstOrNull()?.icon ?: "",
        humidity = current.main.humidity,
        windSpeed = current.wind.speed,
        pressure = current.main.pressure,
        clouds = current.clouds.all
    )
}