package com.example.weatherly.data

import android.location.Location
import com.example.weatherly.core.LocationTracker
import com.example.weatherly.data.repository.LocationRepositoryImpl
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import com.example.weatherly.data.source.remote.RemoteDataSource
import com.example.weatherly.domain.model.CityLocation
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class LocationRepositoryTest {

    private lateinit var locationTracker: LocationTracker
    private lateinit var preferencesDataSource: PreferencesDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repository: LocationRepositoryImpl

    private val fakeCityLocation = CityLocation(
        cityName = "Cairo",
        lat = 30.0444,
        lon = 31.2357,
        state = "Cairo",
        country = "EG"
    )

    @Before
    fun setUp() {
        locationTracker = mockk()
        preferencesDataSource = mockk(relaxed = true)
        remoteDataSource = mockk()
        repository =
            LocationRepositoryImpl(locationTracker, preferencesDataSource, remoteDataSource)
    }

    @Test
    fun `hasLocationPermission returns true when tracker grants permission`() {
        // Arrange
        every { locationTracker.hasLocationPermission() } returns true

        // Act
        val result = repository.hasLocationPermission()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.`is`(true))
    }

    @Test
    fun `hasLocationPermission returns false when tracker denies permission`() {
        // Arrange
        every { locationTracker.hasLocationPermission() } returns false

        // Act
        val result = repository.hasLocationPermission()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.`is`(false))
    }

    @Test
    fun `getCurrentLocation returns location from tracker`() = runTest {
        // Arrange
        val fakeLocation = mockk<Location>()
        coEvery { locationTracker.getCurrentLocation() } returns fakeLocation

        // Act
        val result = repository.getCurrentLocation()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(fakeLocation))
    }

    @Test
    fun `getCurrentLocation returns null when tracker returns null`() = runTest {
        // Arrange
        coEvery { locationTracker.getCurrentLocation() } returns null

        // Act
        val result = repository.getCurrentLocation()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.nullValue()))
    }

    @Test
    fun `isGpsEnabled returns true when GPS is on`() {
        // Arrange
        every { locationTracker.isGpsEnabled() } returns true

        // Act
        val result = repository.isGpsEnabled()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.`is`(true))
    }

    @Test
    fun `isGpsEnabled returns false when GPS is off`() {
        // Arrange
        every { locationTracker.isGpsEnabled() } returns false

        // Act
        val result = repository.isGpsEnabled()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.`is`(false))
    }

    @Test
    fun `getSavedLocation returns pair from preferences`() {
        // Arrange
        val expected = Pair(30.0f, 31.0f)
        every { preferencesDataSource.getSavedLocation() } returns expected

        // Act
        val result = repository.getSavedLocation()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(expected))
    }

    @Test
    fun `saveLocation with Location delegates to preferencesDataSource`() {
        // Arrange
        val fakeLocation = mockk<Location>()

        // Act
        repository.saveLocation(fakeLocation)

        // Assert
        verify(exactly = 1) { preferencesDataSource.saveLocation(fakeLocation) }
    }

    @Test
    fun `saveLocation with lat and lon delegates to preferencesDataSource`() {
        // Arrange
        val lat = 30.0444
        val lon = 31.2357

        // Act
        repository.saveLocation(lat, lon)

        // Assert
        verify(exactly = 1) { preferencesDataSource.saveLocation(lat, lon) }
    }

    @Test
    fun `searchCity returns empty list when remote returns empty`() = runTest {
        // Arrange
        coEvery { remoteDataSource.searchCity(any()) } returns emptyList()

        // Act
        val result = repository.searchCity("Unknown City")

        // Assert
        MatcherAssert.assertThat(result.isEmpty(), CoreMatchers.`is`(true))
    }

    @Test
    fun `searchCity propagates exception from remoteDataSource`() = runTest {
        // Arrange
        coEvery { remoteDataSource.searchCity(any()) } throws RuntimeException("Network error")
        var caughtMessage: String? = null

        // Act
        try {
            repository.searchCity("Cairo")
        } catch (e: RuntimeException) {
            caughtMessage = e.message
        }

        // Assert
        MatcherAssert.assertThat(caughtMessage, CoreMatchers.equalTo("Network error"))
    }

    @Test
    fun `reverseGeocode throws when remote returns empty list`() = runTest {
        // Arrange
        coEvery { remoteDataSource.reverseGeocode(any(), any()) } returns emptyList()
        var thrown = false

        // Act
        try {
            repository.reverseGeocode(0.0, 0.0)
        } catch (e: NoSuchElementException) {
            thrown = true
        }

        // Assert
        MatcherAssert.assertThat(thrown, CoreMatchers.`is`(true))
    }

    @Test
    fun `reverseGeocode propagates exception from remoteDataSource`() = runTest {
        // Arrange
        coEvery {
            remoteDataSource.reverseGeocode(
                any(),
                any()
            )
        } throws RuntimeException("Geocode failed")
        var caughtMessage: String? = null

        // Act
        try {
            repository.reverseGeocode(0.0, 0.0)
        } catch (e: RuntimeException) {
            caughtMessage = e.message
        }

        // Assert
        MatcherAssert.assertThat(caughtMessage, CoreMatchers.equalTo("Geocode failed"))
    }
}