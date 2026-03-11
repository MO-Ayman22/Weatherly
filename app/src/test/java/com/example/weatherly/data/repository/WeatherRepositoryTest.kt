package com.example.weatherly.data.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.data.source.preferences.PreferencesDataSource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var dataSource: PreferencesDataSource

    @InjectMockKs
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun setFirstTime_CalledWithFalse_isFirstTimeReturnsFalse() {
        // Arrange
        every { dataSource.setFirstTime(false) } just Runs
        every { dataSource.isFirstTime() } returns false

        // Act
        repository.setFirstTime(false)
        val result = repository.isFirstTime()

        // Assert
        assertThat(result, `is`(false))
        verify { dataSource.setFirstTime(false) }
        verify { dataSource.isFirstTime() }
    }

    @Test
    fun setFirstTime_CalledWithTrue_isFirstTimeReturnsTrue() {
        // Arrange
        every { dataSource.setFirstTime(true) } just Runs
        every { dataSource.isFirstTime() } returns true

        // Act
        repository.setFirstTime(true)
        val result = repository.isFirstTime()

        // Assert
        assertThat(result, `is`(true))
        verify { dataSource.setFirstTime(true) }
        verify { dataSource.isFirstTime() }
    }


}