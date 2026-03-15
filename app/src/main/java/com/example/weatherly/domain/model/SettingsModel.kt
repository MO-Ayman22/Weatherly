package com.example.weatherly.domain.model

data class SettingsModel(
    val language: String,
    val tempUnit: String,
    val windUnit: String,
    val isNotificationsEnabled: Boolean
)