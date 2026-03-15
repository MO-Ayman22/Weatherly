package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.utils.AppConstants
import kotlin.math.roundToInt

fun ForecastResponseDto.toCurrentWeather(tempUnit: String, windSpeedUnit: String): CurrentWeather {
    val current = list.first()
    val temp = if (tempUnit == AppConstants.FAHRENHEIT) {
        celsiusToFahrenheit(current.main.temp).roundToInt()
    } else {
        current.main.temp.roundToInt()
    }
    val windSpeed = if (windSpeedUnit == AppConstants.KM_H) {
        msToKmh(current.wind.speed)
    } else {
        current.wind.speed
    }
    return CurrentWeather(
        location = city.name,
        temperature = temp,
        feelsLike = current.main.feels_like.roundToInt(),
        condition = current.weather.firstOrNull()?.main ?: "",
        description = current.weather.firstOrNull()?.description ?: "",
        icon = iconMapper(current.weather.firstOrNull()?.icon),
        humidity = current.main.humidity,
        windSpeed = windSpeed,
        pressure = current.main.pressure,
        clouds = current.clouds.all
    )
}