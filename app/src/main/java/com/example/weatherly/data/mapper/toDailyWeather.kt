package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.domain.model.DailyWeather
import java.text.SimpleDateFormat
import java.util.Locale

fun ForecastResponseDto.toDailyWeather(): List<DailyWeather> {
    return list.groupBy { it.dt_txt.substring(0, 10) }
        .map { (date, items) ->
            val avgTemp = items.map { it.main.temp }.average()
            val description = items.first().weather.firstOrNull()?.description ?: ""
            val icon = items.first().weather.firstOrNull()?.icon ?: ""

            val day = try {
                val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val sdfOutput = SimpleDateFormat("EEE", Locale.getDefault())
                val parsedDate = sdfInput.parse(date)
                if (parsedDate != null) {
                    sdfOutput.format(parsedDate)
                } else {
                    date
                }
            } catch (_: Exception) {
                date
            }

            DailyWeather(
                day = day,
                temp = avgTemp,
                description = description,
                icon = icon
            )
        }
}