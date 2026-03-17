package com.example.weatherly.presentation.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route() {
    @Serializable
    object OnBoarding : Route()

    @Serializable
    object MainScreen : Route()

    @Serializable
    object Home : Route()

    @Serializable
    object Favorites : Route()

    @Serializable
    object Settings : Route()

    @Serializable
    object Alerts : Route()

    @Serializable
    object Map : Route()
}