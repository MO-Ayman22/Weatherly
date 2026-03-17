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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.navigation.Route

@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    NavigationBar(
        containerColor = Color(0xFF2D3436),
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            Route.Home to (Icons.Filled.Home to stringResource(R.string.home)),
            Route.Favorites to (Icons.Filled.Favorite to stringResource(R.string.favorites)),
            Route.Alerts to (Icons.Filled.Notifications to stringResource(R.string.alerts)),
            Route.Settings to (Icons.Filled.Settings to stringResource(R.string.settings))
        )

        items.forEach { (route, data) ->
            // هنا السر: بنشيك هل المسار الحالي واصل للـ Route ده فعلاً؟
            val isSelected = currentDestination?.hasRoute(route::class) ?: false

            NavigationBarItem(
                icon = { Icon(data.first, contentDescription = data.second) },
                label = { Text(text = data.second) },
                selected = isSelected,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
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
