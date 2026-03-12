package com.example.weatherly.domain.usecase

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.domain.repository.LocationRepository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SaveLocationUseCaseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var location: Location

    @MockK
    lateinit var repository: LocationRepository

    @InjectMockKs
    private lateinit var useCase: SaveLocationUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_calledWithLocation_repositorySaveLocationCalledWithLocation() {
        // Arrange
        every { useCase(location) } just Runs

        // Act
        useCase(location)

        // Assert
        verify(exactly = 1) { repository.saveLocation(location) }
    }
}
