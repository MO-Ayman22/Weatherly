package com.example.weatherly.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherly.presentation.feature.main.ui.MainScreen
import com.example.weatherly.presentation.feature.main.viewmodel.MainUiState
import com.example.weatherly.presentation.feature.main.viewmodel.MainViewModel
import com.example.weatherly.presentation.feature.onboarding.ui.OnBoardingScreen

@Composable
fun RootNavHost( modifier: Modifier = Modifier,navController: NavHostController, viewModel: MainViewModel = hiltViewModel<MainViewModel>()) {

    val uiState = viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = when (uiState.value) {
            MainUiState.ShowHome -> Route.MainScreen
            MainUiState.ShowOnBoarding -> Route.OnBoarding
        }
        , modifier = modifier
    ) {
        composable<Route.OnBoarding> {
            OnBoardingScreen(
                onFinish = {
                    viewModel.setFirstTime(false)
                    navController.navigate(Route.MainScreen) })
        }
        
        composable<Route.MainScreen> {
            MainScreen()
        }
    }
}