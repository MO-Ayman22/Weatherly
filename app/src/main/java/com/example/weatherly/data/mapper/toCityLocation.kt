package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.GeoApiResponse
import com.example.weatherly.domain.model.CityLocation

fun GeoApiResponse.toCityLocation(): CityLocation {
    return CityLocation(
        cityName = name,
        state = state,
        country = country,
        lat = lat,
        lon = lon
    )
}