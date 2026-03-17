package com.example.weatherly.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherly.presentation.feature.alerts.ui.AlertsScreen
import com.example.weatherly.presentation.feature.favorites.ui.FavoritesScreen
import com.example.weatherly.presentation.feature.home.ui.HomeScreen
import com.example.weatherly.presentation.feature.map.ui.MapScreen
import com.example.weatherly.presentation.feature.settings.ui.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<Route.Home> {
            HomeScreen()
        }

        composable<Route.Favorites> {
            FavoritesScreen(navController = navController)
        }

        composable<Route.Settings> {
            SettingsScreen(navController = navController)
        }

        composable<Route.Alerts> {
            AlertsScreen()
        }

        composable<Route.Map> {
            MapScreen(navController = navController)
        }
    }
}