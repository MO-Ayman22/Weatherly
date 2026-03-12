package com.example.weatherly.presentation.feature.onboarding.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class OnBoardingPage(
    val backgroundColor: Brush,
    val icon: ImageVector,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnBoardingPage(
        backgroundColor = Brush.verticalGradient(listOf(Color(0xFF57A5FF), Color(0xFF8FC5FF))),
        icon = Icons.Default.LocationOn,
        title = "Location Permission",
        description = "Allow us to access your location to provide accurate weather forecasts for your area"
    ),
    OnBoardingPage(
        backgroundColor = Brush.verticalGradient(listOf(Color(0xFFC27DFF), Color(0xFFD9B0FF))),
        icon = Icons.Default.Favorite,
        title = "Save Favorites",
        description = "Add your favorite locations to quickly check weather conditions anywhere in the world"
    ),
    OnBoardingPage(
        backgroundColor = Brush.verticalGradient(listOf(Color(0xFFFF9125), Color(0xFFFFB969))),
        icon = Icons.Default.Notifications,
        title = "Weather Alerts",
        description = "Get notified about severe weather conditions and stay prepared for any changes"
    )
)