package com.example.weatherly.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.domain.repository.LocationRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HasLocationPermissionUseCaseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: LocationRepository

    @InjectMockKs
    private lateinit var useCase: HasLocationPermissionUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_called_repositoryGetHasLocationPermissionCalled() {
        // Arrange
        every { useCase() } returns true

        // Act
        val result = useCase()

        // Assert
        assertThat(result, `is`(true))
        coVerify(exactly = 1) { repository.hasLocationPermission() }
    }
}