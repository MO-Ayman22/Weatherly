package com.example.weatherly.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.domain.repository.LocationRepository
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

class GetSavedLocationUseCaseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: LocationRepository

    @InjectMockKs
    private lateinit var useCase: GetSavedLocationUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_called_repositoryGetSaveLocationCalled() {
        // Arrange
        every { useCase() } returns (1f to 2f)

        // Act
        val result = useCase()

        // Assert
        assertThat(result, `is`(1f to 2f))
        verify(exactly = 1) { repository.getSavedLocation() }
    }
}