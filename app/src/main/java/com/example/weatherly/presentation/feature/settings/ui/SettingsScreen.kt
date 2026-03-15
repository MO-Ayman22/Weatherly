package com.example.weatherly.presentation.feature.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.settings.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state = viewModel.uiState
    val context = LocalContext.current

    val backgroundBrush = Brush.verticalGradient(
        listOf(Color(0xFF9191FF), Color(0xFFEAC0F6))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        if (state.isLoading) {
            GlassLoadingScreen()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 32.dp)
                )

                LanguageSection(
                    selectedLang = state.language,
                    onLangChange = { viewModel.changeLanguage(context = context, lang = it) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                UnitSection(
                    tempUnit = state.tempUnit,
                    windUnit = state.windUnit,
                    onTempChange = { viewModel.changeTempUnit(it) },
                    onWindChange = { viewModel.changeWindUnit(it) }
                )
            }
        }
    }
}