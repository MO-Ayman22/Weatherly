package com.example.weatherly.presentation.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherly.domain.model.DailyWeather

@Composable
fun SevenDayForecastSection(
    modifier: Modifier = Modifier,
    items: List<DailyWeather>
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = "Five Day Forecast",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items.size) {
                DailyItem(item = items[it])
            }
        }
    }
}