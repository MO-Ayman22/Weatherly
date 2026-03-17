package com.example.weatherly.presentation.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import com.example.weatherly.utils.AppConstants.GPS_METHOD_KEY

@Composable
fun LocationSection(
    locationMethod: String,
    onMethodChange: (String) -> Unit
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.location_settings),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Column {
                Text(
                    text = stringResource(R.string.location_source),
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )

                GlassToggle(
                    options = listOf(
                        stringResource(R.string.gps),
                        stringResource(R.string.map)
                    ),
                    selectedIndex = if (locationMethod == GPS_METHOD_KEY) 0 else 1,
                    onOptionSelected = { index ->
                        val method =
                            if (index == 0) GPS_METHOD_KEY else AppConstants.MAP_METHOD_KEY
                        onMethodChange(method)
                    }
                )
            }

            Text(
                text = if (locationMethod == GPS_METHOD_KEY)
                    stringResource(R.string.gps_description)
                else
                    stringResource(R.string.map_description),
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 12.dp, start = 4.dp)
            )
        }
    }
}