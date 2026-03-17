package com.example.weatherly.presentation.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherly.domain.model.HourlyWeather

@Composable
fun HourlyItem(
    item: HourlyWeather,
    tempResId: Int
) {
    Box(
        modifier = Modifier
            .size(width = 112.dp, height = 160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .padding(16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(item.time, color = Color.White)

            Image(
                painter = painterResource(item.icon),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )

            Text(
                "${item.temp}" + stringResource(tempResId),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}