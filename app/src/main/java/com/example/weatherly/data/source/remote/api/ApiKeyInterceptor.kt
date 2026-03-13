package com.example.weatherly.data.source.remote.api

import com.example.weatherly.BuildConfig
import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val newUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter("appid", BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}