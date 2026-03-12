package com.example.weatherly.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController, startDestination: Route, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = startDestination
        , modifier = modifier
    ) {

        composable<Route.Home> {
            // Home Screen
        }

        composable<Route.Favorites> {
            // Favorites Screen
        }

        composable<Route.Settings> {
            // Settings Screen
        }

        composable<Route.Alerts> {
            // Alerts Screen
        }
    }
}
