package com.example.weatherly.data.source.preferences

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferencesDataSourceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataSource: PreferencesDataSource
    private lateinit var prefs: SharedPreferences

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        prefs = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        dataSource = PreferencesDataSourceImpl(prefs)
    }

    @After
    fun tearDown() {
        prefs.edit().clear().apply()
    }

    @Test
    fun setFirstTime_CalledWithFalse_isFirstTimeReturnsFalse() {

        // Given nothing

        // When call setFirstTime with false
        dataSource.setFirstTime(false)

        // Then isFirstTime returns false
        val result = dataSource.isFirstTime()
        assertThat(result, `is`(false))
    }

    @Test
    fun setFirstTime_CalledWithTrue_isFirstTimeReturnsTrue() {

        // Given nothing

        // When call setFirstTime with true
        dataSource.setFirstTime(true)

        // Then isFirstTime returns true
        val result = dataSource.isFirstTime()
        assertThat(result, `is`(true))
    }

    @Test
    fun saveLocation_calledWithLocation_getSavedLocationReturnsCorrectCoordinates() {

        // Arrange
        val location = Location("test").apply {
            latitude = 30.5
            longitude = 31.2
        }

        // Act
        dataSource.saveLocation(location)
        val (lat, lon) = dataSource.getSavedLocation()

        // Assert
        assertThat(lat, `is`(30.5f))
        assertThat(lon, `is`(31.2f))
    }

    @Test
    fun getSavedLocation_whenNothingSaved_returnsDefaultValues() {

        // Act
        val (lat, lon) = dataSource.getSavedLocation()

        // Assert
        assertThat(lat, `is`(0f))
        assertThat(lon, `is`(0f))
    }
}