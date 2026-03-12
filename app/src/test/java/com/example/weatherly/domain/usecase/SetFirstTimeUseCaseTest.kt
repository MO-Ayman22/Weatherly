package com.example.weatherly.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.domain.repository.WeatherRepository
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

class SetFirstTimeUseCaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: WeatherRepository

    @InjectMockKs
    private lateinit var useCase: SetFirstTimeUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_calledWithFalse_repositorySetFirstTimeCalledWithFalse() {
        // Arrange
        every { repository.setFirstTime(false) } just Runs

        // Act
        useCase(false)

        // Assert
        verify(exactly = 1) { repository.setFirstTime(false) }
    }

    @Test
    fun invoke_calledWithTrue_repositorySetFirstTimeCalledWithTrue() {
        // Arrange
        every { repository.setFirstTime(true) } just Runs

        // Act
        useCase(true)

        // Assert
        verify(exactly = 1) { repository.setFirstTime(true) }
    }
}