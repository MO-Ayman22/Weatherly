package com.example.weatherly.data.mapper

import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.FavoriteWeather

fun CurrentWeather.toFavoriteWeather(): FavoriteWeather {
    return FavoriteWeather(
        location = location,
        language = language,
        temperature = temperature,
        feelsLike = feelsLike,
        condition = condition,
        description = description,
        icon = icon,
        humidity = humidity,
        windSpeed = windSpeed,
        pressure = pressure,
        clouds = clouds,
        lat = lat,
        lon = lon,
        lastFetch = System.currentTimeMillis()
    )

}
   