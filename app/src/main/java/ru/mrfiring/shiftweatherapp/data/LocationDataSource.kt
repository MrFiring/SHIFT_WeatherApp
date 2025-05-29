package ru.mrfiring.shiftweatherapp.data

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NoLocationPermission : Exception()

class LocationDataSource(private val context: Context) {

    private val locationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    suspend fun getCurrent(): Location? = suspendCancellableCoroutine { continuation ->
        try {
            locationClient.lastLocation.addOnSuccessListener {
                continuation.resume(it)
            }
        } catch (e: SecurityException) {
            continuation.resumeWithException(NoLocationPermission())
        }
    }
}