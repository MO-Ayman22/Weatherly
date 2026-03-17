package com.example.weatherly.presentation.feature.favorites.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherly.R
import com.example.weatherly.domain.model.FavoriteWeather
import com.example.weatherly.presentation.feature.home.ui.GlassCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteLocationItem(
    favoriteLocation: FavoriteWeather,
    onDelete: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.StartToEnd && !isExpanded) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = !isExpanded,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            val color =
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd && !isExpanded) {
                    Color.Red.copy(alpha = 0.8f)
                } else {
                    Color.Transparent
                }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(color),
                contentAlignment = Alignment.CenterStart
            ) {}
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .animateContentSize()
        ) {

            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = favoriteLocation.location,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = favoriteLocation.condition,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    Image(
                        painter = painterResource(id = favoriteLocation.icon),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )

                    Text(
                        text = "${favoriteLocation.temperature}°",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            if (isExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            Color.White.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                        )
                        .padding(top = 8.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        ConditionItemVertical(
                            icon = painterResource(R.drawable.ic_wind),
                            label = stringResource(R.string.wind_speed),
                            value = "${favoriteLocation.windSpeed}"
                        )
                        ConditionItemVertical(
                            icon = painterResource(R.drawable.ic_humidity),
                            label = stringResource(R.string.humidity),
                            value = "${favoriteLocation.humidity}%"
                        )
                        ConditionItemVertical(
                            icon = painterResource(R.drawable.ic_pressure),
                            label = stringResource(R.string.pressure),
                            value = "${favoriteLocation.pressure}"
                        )
                        ConditionItemVertical(
                            icon = painterResource(R.drawable.ic_cloud),
                            label = stringResource(R.string.clouds),
                            value = "${favoriteLocation.clouds}%"
                        )
                    }
                }
            }
        }
    }

}