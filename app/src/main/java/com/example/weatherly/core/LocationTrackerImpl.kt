package com.example.weatherly.core

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import jakarta.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private val fusedClient: FusedLocationProviderClient,
    private val locationManager: LocationManager,
    private val context: Context
) : LocationTracker {

    override fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {

        return suspendCancellableCoroutine { cont ->
            fusedClient.lastLocation
                .addOnSuccessListener { location ->
                    cont.resume(location)
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
    }

    override fun isGpsEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}