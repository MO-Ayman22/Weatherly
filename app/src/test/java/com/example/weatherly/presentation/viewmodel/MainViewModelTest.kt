package com.example.weatherly.presentation.viewmodel

import com.example.weatherly.domain.usecase.IsFirstTimeUseCase
import com.example.weatherly.domain.usecase.SetFirstTimeUseCase
import com.example.weatherly.presentation.feature.main.viewmodel.MainUiState
import com.example.weatherly.presentation.feature.main.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    @MockK
    lateinit var isFirstTimeUseCase: IsFirstTimeUseCase

    @MockK
    lateinit var setFirstTimeUseCase: SetFirstTimeUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun checkInitialScreen_firstTime_showOnboarding() {
        // Arrange
        every { isFirstTimeUseCase() } returns true

        // Act
        viewModel = MainViewModel(
            isFirstTimeUseCase,
            setFirstTimeUseCase
        )
        val state = viewModel.uiState.value

        // Assert
        verify { isFirstTimeUseCase() }
        assertThat(state, `is`(MainUiState.ShowOnBoarding))
    }
    @Test
    fun checkInitialScreen_notFirstTime_showHome() {
        // Arrange
        every { isFirstTimeUseCase() } returns false

        // Act
        viewModel = MainViewModel(
            isFirstTimeUseCase,
            setFirstTimeUseCase
        )
        val state = viewModel.uiState.value

        // Assert
        verify { isFirstTimeUseCase() }
        assertThat(state, `is`(MainUiState.ShowHome))
    }

    @Test
    fun setFirstTime_callsUseCase() {
        // Arrange
        every { isFirstTimeUseCase() } returns false
        every { setFirstTimeUseCase(false) } just Runs

        // Act
        viewModel = MainViewModel(
            isFirstTimeUseCase,
            setFirstTimeUseCase
        )
        viewModel.setFirstTime(false)

        // Assert
        verify { setFirstTimeUseCase(false) }
    }
}