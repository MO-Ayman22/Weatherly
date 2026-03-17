package com.example.weatherly.presentation.viewmodel

import com.example.weatherly.domain.repository.LocationRepository
import com.example.weatherly.domain.repository.SettingsRepository
import com.example.weatherly.presentation.feature.settings.viewmodel.SettingsViewModel
import com.example.weatherly.utils.AppConstants
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var settingsRepository: SettingsRepository
    private lateinit var locationRepository: LocationRepository
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        settingsRepository = mockk(relaxed = true)
        locationRepository = mockk(relaxed = true)
        every { settingsRepository.getLanguage() } returns "en"
        every { settingsRepository.getTempUnit() } returns "celsius"
        every { settingsRepository.getWindSpeedUnit() } returns "m/s"
        every { settingsRepository.getLocationMethod() } returns "gps"
        viewModel = SettingsViewModel(settingsRepository, locationRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadSettings populates uiState on init`() = runTest {
        // Arrange - stubs set in setUp

        // Act
        advanceUntilIdle()

        // Assert
        assertThat(viewModel.uiState.language, equalTo("en"))
        assertThat(viewModel.uiState.tempUnit, equalTo("celsius"))
        assertThat(viewModel.uiState.windUnit, equalTo("m/s"))
        assertThat(viewModel.uiState.locationMethod, equalTo("gps"))
        assertThat(viewModel.uiState.isLoading, `is`(false))
    }

    @Test
    fun `changeTempUnit saves unit and updates uiState`() = runTest {
        // Arrange
        advanceUntilIdle()

        // Act
        viewModel.changeTempUnit("fahrenheit")
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { settingsRepository.saveTempUnit("fahrenheit") }
        assertThat(viewModel.uiState.tempUnit, equalTo("fahrenheit"))
    }

    @Test
    fun `changeWindUnit saves unit and updates uiState`() = runTest {
        // Arrange
        advanceUntilIdle()

        // Act
        viewModel.changeWindUnit("km/h")
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { settingsRepository.saveWindSpeedUnit("km/h") }
        assertThat(viewModel.uiState.windUnit, equalTo("km/h"))
    }

    @Test
    fun `changeLocationMethod saves method and updates uiState`() = runTest {
        // Arrange
        advanceUntilIdle()

        // Act
        viewModel.changeLocationMethod("map")
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { settingsRepository.saveLocationMethod("map") }
        assertThat(viewModel.uiState.locationMethod, equalTo("map"))
    }

    @Test
    fun `isGpsEnabled returns value from locationRepository`() {
        // Arrange
        every { locationRepository.isGpsEnabled() } returns true

        // Act
        val result = viewModel.isGpsEnabled()

        // Assert
        assertThat(result, `is`(true))
    }

    @Test
    fun `onPermissionResult sets location method to GPS`() = runTest {
        // Arrange
        advanceUntilIdle()

        // Act
        viewModel.onPermissionResult()
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { settingsRepository.saveLocationMethod(AppConstants.GPS_METHOD_KEY) }
    }
}