package com.example.weatherly.presentation.feature.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.navigation.Route

@Composable
fun BottomNavBar(
    selectedRoute: Route,
    onItemClick: (Route) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF77A7D3).copy(alpha = 0.5f),
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            Route.Home to (Icons.Filled.Home to stringResource(R.string.home)),
            Route.Favorites to (Icons.Filled.Favorite to stringResource(R.string.favorites)),
            Route.Alerts to (Icons.Filled.Notifications to stringResource(R.string.alerts)),
            Route.Settings to (Icons.Filled.Settings to stringResource(R.string.settings))
        )

        items.forEach { (route, data) ->
            NavigationBarItem(
                icon = { Icon(data.first, contentDescription = data.second) },
                label = { Text(text = data.second) },
                selected = selectedRoute == route,
                onClick = { onItemClick(route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF57A5FF),
                    unselectedIconColor = Color.White.copy(alpha = 0.7f),
                    selectedTextColor = Color(0xFF57A5FF),
                    unselectedTextColor = Color.White.copy(alpha = 0.7f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
