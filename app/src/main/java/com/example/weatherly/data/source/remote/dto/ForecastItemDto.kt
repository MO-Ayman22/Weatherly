package com.example.weatherly.data.source.remote.dto

data class ForecastItemDto(
    val dt: Long,
    val main: MainDto,
    val weather: List<WeatherDto>,
    val clouds: CloudsDto,
    val wind: WindDto,
    val visibility: Int,
    val pop: Double,
    val rain: RainDto?,
    val sys: SysDto,
    val dt_txt: String
)