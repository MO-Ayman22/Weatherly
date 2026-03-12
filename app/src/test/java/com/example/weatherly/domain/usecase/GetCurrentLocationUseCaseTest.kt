package com.example.weatherly.domain.usecase

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.domain.repository.LocationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCurrentLocationUseCaseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var location: Location

    @MockK
    lateinit var repository: LocationRepository

    @InjectMockKs
    private lateinit var useCase: GetCurrentLocationUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_called_repositoryGetCurrentLocationCalled() = runTest {
        // Arrange
        coEvery { useCase() } returns location

        // Act
        val result = useCase()

        // Assert
        assertThat(result, `is`(location))
        coVerify(exactly = 1) { repository.getCurrentLocation() }
    }
}