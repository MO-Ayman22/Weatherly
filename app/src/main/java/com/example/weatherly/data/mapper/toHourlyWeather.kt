package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.domain.model.HourlyWeather
import java.text.SimpleDateFormat
import java.util.Locale

fun ForecastResponseDto.toHourlyWeather(): List<HourlyWeather> {
    val sdfInput = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val sdfOutput = SimpleDateFormat("HH:mm", Locale.getDefault())

    return list.take(24).map { item ->
        val time = try {
            val date = sdfInput.parse(item.dt_txt)
            if (date != null) {
                sdfOutput.format(date)
            } else {
                item.dt_txt
            }
        } catch (_: Exception) {
            item.dt_txt
        }

        HourlyWeather(
            time = time,
            temp = item.main.temp,
            condition = item.weather.firstOrNull()?.main ?: "",
            icon = item.weather.firstOrNull()?.icon ?: ""
        )
    }
}