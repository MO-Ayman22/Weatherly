package com.example.weatherly.data.mapper

import com.example.weatherly.data.source.remote.dto.ForecastResponseDto
import com.example.weatherly.domain.model.HourlyWeather
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

fun ForecastResponseDto.toHourlyWeather(): List<HourlyWeather> {

    val sdfInput = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val sdfOutput = SimpleDateFormat("HH:mm", Locale.getDefault())

    val result = mutableListOf<HourlyWeather>()

    list.take(8).forEach { item ->

        val baseDate = try {
            sdfInput.parse(item.dt_txt)
        } catch (_: Exception) {
            null
        }

        repeat(3) { hourOffset ->

            val time = if (baseDate != null) {
                val cal = java.util.Calendar.getInstance()
                cal.time = baseDate
                cal.add(java.util.Calendar.HOUR_OF_DAY, hourOffset)
                sdfOutput.format(cal.time)
            } else {
                item.dt_txt
            }

            result.add(
                HourlyWeather(
                    time = time,
                    temp = item.main.temp.roundToInt(),
                    condition = item.weather.firstOrNull()?.main ?: "",
                    icon = iconMapper(item.weather.firstOrNull()?.icon)
                )
            )
        }
    }

    return result.take(24)
}