package ru.mrfiring.shiftweatherapp.data

import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.location.LocationRepository

class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun getCurrent(): Location? {
        val locationModel = locationDataSource.getCurrent()

        return locationModel?.let {
            Location(it.latitude, it.longitude)
        }
    }
}