package com.example.weatherly.presentation.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.HourlyWeather

@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    current: CurrentWeather,
    hourly: List<HourlyWeather>,
    daily: List<DailyWeather>,
    date: String,
) {

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF57A5FF),
            Color(0xFF8FC5FF)
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 24.dp
            )
        ) {

            item {
                HeaderSection(
                    location = current.location,
                    date = date
                )
            }

            item {
                CurrentWeatherSection(
                    temperature = "${current.temperature}°",
                    condition = current.condition,
                    conditionDescription = current.description,
                    icon = current.icon
                )
            }

            item {
                DetailedConditionsSection(
                    humidityValue = "${current.humidity}%",
                    windValue = "${current.windSpeed} m/s",
                    pressureValue = "${current.pressure} hPa",
                    cloudsValue = "${current.clouds}%"
                )
            }

            item {
                HourlyForecastSection(
                    items = hourly
                )
            }

            item {
                Text(
                    text = "Seven Day Forecast",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                daily.forEach { day ->
                    DailyItem(day)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}