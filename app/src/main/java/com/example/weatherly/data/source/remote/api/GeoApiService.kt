package com.example.weatherly.data.source.remote.api

import com.example.weatherly.data.source.remote.dto.GeoApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApiService {

    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") city: String,
        @Query("limit") limit: Int = 5,
    ): List<GeoApiResponse>

    @GET("geo/1.0/reverse")
    suspend fun reverseGeocode(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int = 1,
    ): List<GeoApiResponse>
}
