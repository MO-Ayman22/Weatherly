package com.example.weatherly.presentation.feature.favorites.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherly.presentation.feature.favorites.viewmodel.FavoriteViewModel
import com.example.weatherly.presentation.feature.navigation.Route
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favorites by viewModel.favoritesState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val navBackStackEntry = navController.currentBackStackEntry
    val lat = navBackStackEntry?.savedStateHandle?.get<Double>("lat")
    val lon = navBackStackEntry?.savedStateHandle?.get<Double>("lon")

    LaunchedEffect(lat, lon) {
        if (lat != null && lon != null) {
            viewModel.addNewLocation(lat, lon)
            navBackStackEntry.savedStateHandle.remove<Double>("lat")
            navBackStackEntry.savedStateHandle.remove<Double>("lon")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is FavoriteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message, withDismissAction = true)
                }
            }
        }
    }

    Scaffold(
        topBar = { FavoritesTopBar(favorites.size) },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(16.dp)
                ) {
                    Text(text = data.visuals.message, color = Color.White)
                }
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f))
                    .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                    .clickable { navController.navigate(Route.Map) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AddLocationAlt,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF9D53FF), Color(0xFFD9B0FF))))
                .padding(innerPadding)
        ) {
            if (favorites.isEmpty()) {
                EmptyFavoritesState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
                ) {
                    items(favorites, key = { it.location }) { favorite ->
                        FavoriteLocationItem(
                            favoriteLocation = favorite,
                            onDelete = { viewModel.deleteLocation(favorite.lat, favorite.lon) }
                        )
                    }
                }
            }
        }
    }
}