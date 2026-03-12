package com.example.weatherly.presentation.feature.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.weatherly.presentation.feature.navigation.Route

@Composable
fun BottomNavBar(
    selectedRoute: Route,
    onItemClick: (Route) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedRoute == Route.Home,
            onClick = { onItemClick(Route.Home) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = selectedRoute == Route.Favorites,
            onClick = { onItemClick(Route.Favorites) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Alerts") },
            label = { Text("Alerts") },
            selected = selectedRoute == Route.Alerts,
            onClick = { onItemClick(Route.Alerts) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = selectedRoute == Route.Settings,
            onClick = { onItemClick(Route.Settings) }
        )
    }
}
