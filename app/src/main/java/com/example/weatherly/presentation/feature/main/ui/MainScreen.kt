package com.example.weatherly.presentation.feature.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherly.presentation.feature.navigation.AppNavHost
import com.example.weatherly.presentation.feature.navigation.Route

@Composable
fun MainScreen() {

    val navController: NavHostController = rememberNavController()
    var selectedRoute by remember { mutableStateOf<Route>(Route.Home) }

    Scaffold(
        topBar = {},
        bottomBar = {
            BottomNavBar(
                selectedRoute = selectedRoute,
                onItemClick = { route ->
                    selectedRoute = route
                    navController.navigate(selectedRoute) {
                        launchSingleTop = true
                        restoreState = true
                    }

                }
            )
        }
    ) { padding ->

        AppNavHost(
            navController = navController,
            startDestination = Route.Home,
            modifier = Modifier.padding(padding)
        )
    }
}