package com.example.weatherly.data.repository

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.core.LocationTracker
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var location: Location

    @MockK
    lateinit var tracker: LocationTracker

    @MockK
    lateinit var dataSource: PreferencesDataSource

    @InjectMockKs
    lateinit var repository: LocationRepositoryImpl


    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getCurrentLocation_returnsTrackerLocation() = runTest {
        // Arrange
        coEvery { tracker.getCurrentLocation() } returns location

        // Act
        val result = repository.getCurrentLocation()

        // Assert
        assertThat(result, `is`(location))
        coVerify { tracker.getCurrentLocation() }
    }

    @Test
    fun isGpsEnabled_returnsTrackerValue() {
        // Arrange
        every { tracker.isGpsEnabled() } returns true

        // Act
        val result = repository.isGpsEnabled()

        // Assert
        assertThat(result, `is`(true))
        verify { tracker.isGpsEnabled() }
    }

    @Test
    fun saveLocation_called_repositoryCallsDataSource() {
        // Arrange
        every { dataSource.saveLocation(location) } just Runs

        // Act
        repository.saveLocation(location)

        // Assert
        verify { dataSource.saveLocation(location) }
    }

    @Test
    fun getSavedLocation_returnsLocationFromPrefs() {
        // Arrange
        every { dataSource.getSavedLocation() } returns (1f to 2f)

        // Act
        val result = repository.getSavedLocation()

        // Assert
        assertThat(result, `is`(1f to 2f))
        verify { dataSource.getSavedLocation() }
    }
}