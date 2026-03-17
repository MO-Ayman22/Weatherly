package com.example.weatherly.presentation.feature.favorites.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherly.R

@Composable
fun FavoritesTopBar(count: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.15f))
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .statusBarsPadding()
    ) {
        Text(
            text = stringResource(R.string.favorite_locations),
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (count > 0)
                stringResource(
                    R.string.you_have_saved_locations_stay_updated_with_their_current_weather_conditions,
                    count
                )
            else
                stringResource(R.string.your_favorite_list_is_empty_start_adding_locations_to_track_them_easily),
            color = Color.White.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}