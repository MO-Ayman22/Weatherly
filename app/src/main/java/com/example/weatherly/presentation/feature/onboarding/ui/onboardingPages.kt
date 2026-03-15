package com.example.weatherly.presentation.feature.onboarding.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.weatherly.R

data class OnBoardingPage(
    val backgroundColor: Brush,
    val icon: ImageVector,
    val title: Int,
    val description: Int
)

val onboardingPages = listOf(
    OnBoardingPage(
        backgroundColor = Brush.verticalGradient(listOf(Color(0xFF57A5FF), Color(0xFF8FC5FF))),
        icon = Icons.Default.LocationOn,
        title = R.string.location_permission,
        description = R.string.allow_us_to_access_your_location_to_provide_accurate_weather_forecasts_for_your_area
    ),
    OnBoardingPage(
        backgroundColor = Brush.verticalGradient(listOf(Color(0xFFC27DFF), Color(0xFFD9B0FF))),
        icon = Icons.Default.Favorite,
        title = R.string.save_favorites,
        description = R.string.add_your_favorite_locations_to_quickly_check_weather_conditions_anywhere_in_the_world
    ),
    OnBoardingPage(
        backgroundColor = Brush.verticalGradient(listOf(Color(0xFFFF9125), Color(0xFFFFB969))),
        icon = Icons.Default.Notifications,
        title = R.string.weather_alerts,
        description = R.string.get_notified_about_severe_weather_conditions_and_stay_prepared_for_any_changes
    )
)