package com.example.weatherly.presentation.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.home.ui.GlassCard
import com.example.weatherly.utils.AppConstants

@Composable
fun UnitSection(
    tempUnit: String,
    windUnit: String,
    onTempChange: (String) -> Unit,
    onWindChange: (String) -> Unit
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.SettingsSuggest,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.measurement_units),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            UnitRow(
                label = stringResource(R.string.temperature),
                options = listOf(stringResource(R.string.c), stringResource(R.string.f)),
                selectedIndex = if (tempUnit == AppConstants.METRIC) 0 else 1,
                onSelectionChange = { index ->
                    val unit = if (index == 0) AppConstants.METRIC else AppConstants.FAHRENHEIT
                    onTempChange(unit)
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.White.copy(alpha = 0.15f)
            )

            UnitRow(
                label = stringResource(R.string.wind_speed),
                options = listOf(
                    stringResource(R.string.wind_speed_ms_unit),
                    stringResource(R.string.wind_speed_kmh_unit)
                ),
                selectedIndex = if (windUnit == AppConstants.M_S) 0 else 1,
                onSelectionChange = { index ->
                    val unit = if (index == 0) AppConstants.M_S else AppConstants.KM_H
                    onWindChange(unit)
                }
            )
        }
    }
}

@Composable
fun UnitRow(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit
) {
    Column {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        GlassToggle(
            options = options,
            selectedIndex = selectedIndex,
            onOptionSelected = onSelectionChange
        )
    }
}