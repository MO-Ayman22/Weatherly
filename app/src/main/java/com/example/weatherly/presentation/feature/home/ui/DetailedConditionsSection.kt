package com.example.weatherly.presentation.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherly.R

@Composable
fun DetailedConditionsSection(
    humidityValue: String,
    windValue: String,
    pressureValue: String,
    cloudsValue: String
) {

    GlassCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ConditionItem(
                    icon = painterResource(R.drawable.ic_humidity),
                    label = "Humidity",
                    value = humidityValue,
                    modifier = Modifier.weight(1f)
                )

                ConditionItem(
                    icon = painterResource(R.drawable.ic_wind),
                    label = "Wind",
                    value = windValue,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ConditionItem(
                    icon = painterResource(R.drawable.ic_pressure),
                    label = "Pressure",
                    value = pressureValue,
                    modifier = Modifier.weight(1f)
                )

                ConditionItem(
                    icon = painterResource(R.drawable.ic_cloud),
                    label = "Clouds",
                    value = cloudsValue,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
