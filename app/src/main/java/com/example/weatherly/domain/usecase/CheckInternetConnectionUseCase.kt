package com.example.weatherly.domain.usecase

import com.example.weatherly.core.NetworkChecker
import jakarta.inject.Inject

class CheckInternetConnectionUseCase @Inject constructor(
    private val networkChecker: NetworkChecker
) {
    operator fun invoke(): Boolean {
        return networkChecker.isConnected()
    }
}