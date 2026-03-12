package com.example.weatherly.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.domain.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class IsFirstTimeUseCaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: WeatherRepository

    @InjectMockKs
    private lateinit var useCase: IsFirstTimeUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_repositoryReturnsTrue_returnsTrue() {
        // Arrange
        every { repository.isFirstTime() } returns true

        // Act
        val result = useCase()

        // Assert
        assertThat(result, `is`(true))
        verify(exactly = 1) { repository.isFirstTime() }
    }

    @Test
    fun invoke_repositoryReturnsFalse_returnsFalse() {
        // Arrange
        every { repository.isFirstTime() } returns false

        // Act
        val result = useCase()

        // Assert
        assertThat(result, `is`(false))
        verify(exactly = 1) { repository.isFirstTime() }
    }
}