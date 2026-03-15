package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

fun ForecastResponseDto.toDailyWeather(tempUnit: String): List<DailyWeather> {
    return list
        .groupBy { it.dt_txt.substring(0, 10) }
        .map { (date, items) ->

            val avgTemp = items.map { it.main.temp }.average()
            val description = items.first().weather.firstOrNull()?.description ?: ""
            val icon = items.first().weather.firstOrNull()?.icon ?: ""

            val day = try {
                val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val sdfOutput = SimpleDateFormat("EEE", Locale.getDefault())
                val parsedDate = sdfInput.parse(date)
                parsedDate?.let { sdfOutput.format(it) } ?: date
            } catch (_: Exception) {
                date
            }
            val temp = if (tempUnit == AppConstants.FAHRENHEIT) {
                celsiusToFahrenheit(avgTemp).roundToInt()
            } else {
                avgTemp.roundToInt()
            }
            DailyWeather(
                day = day,
                temp = temp,
                description = description,
                date = date,
                icon = iconMapper(icon)
            )
        }
        .take(5)
}