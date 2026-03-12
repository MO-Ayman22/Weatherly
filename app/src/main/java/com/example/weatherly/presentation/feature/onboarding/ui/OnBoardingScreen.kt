package com.example.weatherly.presentation.feature.onboarding.ui


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.weatherly.presentation.feature.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch
import android.provider.Settings
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun OnBoardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    val locationState by viewModel.locationState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.onPermissionResult(granted)
    }

    // Recheck GPS when user returns from settings
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.checkPermissionAndGps()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { onboardingPages.size }
    )
    val scope = rememberCoroutineScope()

    HorizontalPager(state = pagerState, userScrollEnabled = false) { page ->

        OnboardingItem(
            page = onboardingPages[page],
            pageIndex = page,
            isLastPage = page == onboardingPages.lastIndex,
            locationState = if (page == 0) locationState else null,
            onNextClick = {
                if (page == onboardingPages.lastIndex) {
                    onFinish()
                } else {
                    scope.launch { pagerState.animateScrollToPage(page + 1) }
                }
            },
            onRequestPermission = {
                permissionLauncher.launch(ACCESS_FINE_LOCATION)
            },
            onOpenGpsSettings = {
                context.startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
        )
    }
}