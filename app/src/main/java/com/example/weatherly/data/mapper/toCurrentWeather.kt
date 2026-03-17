package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.domain.model.CurrentWeather
import kotlin.math.roundToInt

fun ForecastResponseDto.toCurrentWeather(lang: String): CurrentWeather {
    val current = list.first()

    return CurrentWeather(
        location = city.name,
        temperature = current.main.temp.roundToInt(),
        feelsLike = current.main.feels_like.roundToInt(),
        condition = current.weather.firstOrNull()?.main ?: "",
        description = current.weather.firstOrNull()?.description ?: "",
        icon = iconMapper(current.weather.firstOrNull()?.icon),
        humidity = current.main.humidity,
        windSpeed = current.wind.speed.roundToInt(),
        pressure = current.main.pressure,
        clouds = current.clouds.all,
        language = lang,
        lat = city.coord.lat,
        lon = city.coord.lon,
    )
}