package com.example.weatherly.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherly.presentation.feature.alerts.ui.AlertsScreen
import com.example.weatherly.presentation.feature.home.ui.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: Route, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = startDestination
        , modifier = modifier
    ) {

        composable<Route.Home> {
            HomeScreen()
        }

        composable<Route.Favorites> {
            // Favorites Screen
        }

        composable<Route.Settings> {
            // Settings Screen
        }

        composable<Route.Alerts> {
            AlertsScreen()
        }
    }
}
