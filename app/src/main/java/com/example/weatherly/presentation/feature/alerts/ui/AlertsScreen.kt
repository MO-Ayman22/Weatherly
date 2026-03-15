package com.example.weatherly.presentation.feature.alerts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.weatherly.presentation.feature.alerts.viewmodel.AlertViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(viewModel: AlertViewModel = hiltViewModel()) {
    val alerts by viewModel.alerts.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF9125), Color(0xFFFFB969))
                )
            )
            .padding(vertical = 24.dp)
    ) {
        LazyColumn {

            items(
                alerts,
                key = { it.alarmType }
            ) { alert ->

                AlertItem(
                    alert = alert,
                    onToggle = { updatedAlert, enabled ->
                        viewModel.toggleAlert(updatedAlert, enabled)
                    },
                    onApply = { updatedAlert ->
                        viewModel.updateAlert(updatedAlert)
                    }
                )

            }
        }
    }
}
