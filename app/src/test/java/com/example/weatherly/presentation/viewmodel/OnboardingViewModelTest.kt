package com.example.weatherly.presentation.viewmodel

import android.location.Location
import com.example.weatherly.domain.usecase.GetCurrentLocationUseCase
import com.example.weatherly.domain.usecase.HasLocationPermissionUseCase
import com.example.weatherly.domain.usecase.IsGpsEnabledUseCase
import com.example.weatherly.domain.usecase.SaveLocationUseCase
import com.example.weatherly.presentation.feature.onboarding.viewmodel.LocationState
import com.example.weatherly.presentation.feature.onboarding.viewmodel.OnboardingViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class OnboardingViewModelTest {
    @MockK
    lateinit var isGpsEnabledUseCase: IsGpsEnabledUseCase

    @MockK
    lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @MockK
    lateinit var saveLocationUseCase: SaveLocationUseCase

    @MockK
    lateinit var hasLocationPermissionUseCase: HasLocationPermissionUseCase

    private lateinit var viewModel: OnboardingViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun checkPermissionAndGps_permissionDenied_stateNeedPermission() {

        // Arrange
        every { hasLocationPermissionUseCase() } returns false


        // Act
        viewModel = OnboardingViewModel(
            isGpsEnabledUseCase,
            getCurrentLocationUseCase,
            saveLocationUseCase,
            hasLocationPermissionUseCase
        )
        val state = viewModel.locationState.value

        // Assert
        assertThat(state, `is`(LocationState.NeedPermission))
        verify { viewModel.checkPermissionAndGps()}
        verify { hasLocationPermissionUseCase() }
    }

    @Test
    fun checkPermissionAndGps_gpsDisabled_stateGpsDisabled() {

        // Arrange
        every { hasLocationPermissionUseCase() } returns true
        every { isGpsEnabledUseCase() } returns false

        // Act
        viewModel = OnboardingViewModel(
            isGpsEnabledUseCase,
            getCurrentLocationUseCase,
            saveLocationUseCase,
            hasLocationPermissionUseCase
        )

        val state = viewModel.locationState.value

        // Assert
        verify { isGpsEnabledUseCase() }
        assertThat(state, `is`(LocationState.GpsDisabled))
    }

    @Test
    fun checkPermissionAndGps_permissionGrantedAndGpsEnabled_stateGranted() {

        // Arrange
        every { hasLocationPermissionUseCase() } returns true
        every { isGpsEnabledUseCase() } returns true

        // Act
        viewModel = OnboardingViewModel(
            isGpsEnabledUseCase,
            getCurrentLocationUseCase,
            saveLocationUseCase,
            hasLocationPermissionUseCase
        )
        val state = viewModel.locationState.value

        // Assert
        verify { isGpsEnabledUseCase() }
        assertThat(state, `is`(LocationState.Granted))
    }

    @Test
    fun getLocation_locationReturned_saveLocationCalled() = runTest {

        // Arrange
        val location = mockk<Location>()
        coEvery { getCurrentLocationUseCase() } returns location
        every { saveLocationUseCase(location) } just Runs
        every { hasLocationPermissionUseCase() } returns true
        every { isGpsEnabledUseCase() } returns true

        // Act
        viewModel = OnboardingViewModel(
            isGpsEnabledUseCase,
            getCurrentLocationUseCase,
            saveLocationUseCase,
            hasLocationPermissionUseCase
        )
        viewModel.getLocation()

        // Assert
        coVerify { getCurrentLocationUseCase() }
        verify { saveLocationUseCase(location) }
    }
}