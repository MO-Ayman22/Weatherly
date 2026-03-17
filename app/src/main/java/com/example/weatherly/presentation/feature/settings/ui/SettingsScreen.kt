package com.example.weatherly.presentation.feature.settings.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherly.presentation.feature.navigation.Route
import com.example.weatherly.presentation.feature.settings.viewmodel.SettingsViewModel
import com.example.weatherly.utils.AppConstants

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val backgroundBrush = Brush.verticalGradient(
        listOf(Color(0xFF9191FF), Color(0xFFEAC0F6))
    )

    val navBackStackEntry = navController.currentBackStackEntry
    val lat = navBackStackEntry?.savedStateHandle?.get<Double>("lat")
    val lon = navBackStackEntry?.savedStateHandle?.get<Double>("lon")

    LaunchedEffect(lat, lon) {
        if (lat != null && lon != null) {
            viewModel.saveMapLocation(lat, lon)
            navBackStackEntry.savedStateHandle.remove<Double>("lat")
            navBackStackEntry.savedStateHandle.remove<Double>("lon")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SettingsViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message, withDismissAction = true)
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) {
            if (viewModel.isGpsEnabled()) viewModel.onPermissionResult()
            else context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
    }

    Scaffold(
        topBar = { SettingsTopBar() },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(16.dp)
                ) {
                    Text(text = data.visuals.message, color = Color.White)
                }
            }
        },
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                GlassLoadingScreen()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 20.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                            LanguageSection(
                                selectedLang = state.language,
                                onLangChange = { viewModel.changeLanguage(context, it) }
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                    item {
                        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                            UnitSection(
                                tempUnit = state.tempUnit,
                                windUnit = state.windUnit,
                                onTempChange = { viewModel.changeTempUnit(it) },
                                onWindChange = { viewModel.changeWindUnit(it) }
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                    item {
                        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                            LocationSection(
                                locationMethod = state.locationMethod,
                                onMethodChange = { method ->
                                    if (method == AppConstants.GPS_METHOD_KEY) {
                                        permissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    } else {
                                        navController.navigate(Route.Map)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}