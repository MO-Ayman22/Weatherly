package com.example.weatherly.presentation.feature.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.weatherly.presentation.feature.navigation.RootNavHost
import com.example.weatherly.presentation.theme.WeatherlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            WeatherlyTheme {
                RootNavHost(
                    navController = rememberNavController(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}