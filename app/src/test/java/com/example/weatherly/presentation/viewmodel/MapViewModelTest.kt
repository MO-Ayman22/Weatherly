package com.example.weatherly.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.core.NetworkChecker
import com.example.weatherly.domain.model.CityLocation
import com.example.weatherly.domain.repository.LocationRepository
import com.example.weatherly.presentation.feature.map.viewmodel.MapViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var locationRepository: LocationRepository
    private lateinit var networkChecker: NetworkChecker
    private lateinit var viewModel: MapViewModel

    private val fakeCityLocation = CityLocation(
        cityName = "Cairo",
        lat = 30.0444,
        lon = 31.2357,
        state = "cairo",
        country = "EG"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        locationRepository = mockk()
        networkChecker = mockk()
        viewModel = MapViewModel(locationRepository, networkChecker)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchQueryChange sets errorMessage when no internet`() {
        // Arrange
        every { networkChecker.isConnected() } returns false

        // Act
        viewModel.onSearchQueryChange("Cairo")

        // Assert
        assertEquals("No internet connection", viewModel.errorMessage)
    }

    @Test
    fun `onSearchQueryChange does not launch search when no internet`() = runTest {
        // Arrange
        every { networkChecker.isConnected() } returns false

        // Act
        viewModel.onSearchQueryChange("Cairo")
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.searchResults.isEmpty())
        assertFalse(viewModel.isSearching)
    }


    @Test
    fun `onSearchQueryChange updates searchQuery`() {
        // Arrange
        every { networkChecker.isConnected() } returns true
        coEvery { locationRepository.searchCity(any()) } returns emptyList()

        // Act
        viewModel.onSearchQueryChange("Cairo")

        // Assert
        assertEquals("Cairo", viewModel.searchQuery)
    }

    @Test
    fun `onSearchQueryChange sets isSearching to false after results are loaded`() = runTest {
        // Arrange
        every { networkChecker.isConnected() } returns true
        coEvery { locationRepository.searchCity(any()) } returns listOf(fakeCityLocation)

        // Act
        viewModel.onSearchQueryChange("Cairo")
        advanceUntilIdle()

        // Assert
        assertFalse(viewModel.isSearching)
    }


    @Test
    fun `onMapLongPress sets errorMessage when no internet`() = runTest {
        // Arrange
        every { networkChecker.isConnected() } returns false

        // Act
        viewModel.onMapLongPress(30.0, 31.0)

        // Assert
        assertEquals("No internet connection", viewModel.errorMessage)
    }

    @Test
    fun `onMapLongPress sets selectedLocation and searchQuery on success`() = runTest {
        // Arrange
        every { networkChecker.isConnected() } returns true
        coEvery { locationRepository.reverseGeocode(30.0444, 31.2357) } returns fakeCityLocation

        // Act
        viewModel.onMapLongPress(30.0444, 31.2357)
        advanceUntilIdle()

        // Assert
        assertEquals(fakeCityLocation, viewModel.selectedLocation)
        assertEquals("Cairo", viewModel.searchQuery)
    }

    @Test
    fun `onMapLongPress sets errorMessage on repository exception`() = runTest {
        // Arrange
        every { networkChecker.isConnected() } returns true
        coEvery { locationRepository.reverseGeocode(any(), any()) } throws RuntimeException()

        // Act
        viewModel.onMapLongPress(0.0, 0.0)
        advanceUntilIdle()

        // Assert
        assertEquals("Could not fetch city details", viewModel.errorMessage)
    }

    @Test
    fun `onLocationSelected sets selectedLocation`() {
        // Arrange

        // Act
        viewModel.onLocationSelected(fakeCityLocation)

        // Assert
        assertEquals(fakeCityLocation, viewModel.selectedLocation)
    }

    @Test
    fun `onLocationSelected clears searchResults`() {
        // Arrange
        viewModel.searchResults = listOf(fakeCityLocation)

        // Act
        viewModel.onLocationSelected(fakeCityLocation)

        // Assert
        assertTrue(viewModel.searchResults.isEmpty())
    }

    @Test
    fun `onLocationSelected sets searchQuery to city name`() {
        // Arrange

        // Act
        viewModel.onLocationSelected(fakeCityLocation)

        // Assert
        assertEquals("Cairo", viewModel.searchQuery)
    }

    @Test
    fun `errorShown clears errorMessage`() {
        // Arrange
        every { networkChecker.isConnected() } returns false
        viewModel.onSearchQueryChange("Cairo") // triggers an error

        // Act
        viewModel.errorShown()

        // Assert
        assertNull(viewModel.errorMessage)
    }

}