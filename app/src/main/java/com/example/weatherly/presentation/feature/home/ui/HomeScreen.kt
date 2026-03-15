package com.example.weatherly.presentation.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.home.viewmodels.HomeUiState
import com.example.weatherly.presentation.feature.home.viewmodels.HomeViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refreshWeatherManually() },
        modifier = modifier.fillMaxSize(),
        state = pullRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = pullRefreshState
            )
        }
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            when (uiState) {

                HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                is HomeUiState.Success -> {
                    val data = uiState as HomeUiState.Success

                    WeatherContent(
                        current = data.current,
                        hourly = data.hourly,
                        daily = data.daily,
                        date = data.date
                    )
                }

                else -> {
                    NoConnectionView(
                        message = stringResource(R.string.no_internet_connection),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}