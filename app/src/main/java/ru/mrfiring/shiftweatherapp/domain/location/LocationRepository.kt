package ru.mrfiring.shiftweatherapp.domain.location

interface LocationRepository {

    suspend fun getCurrent(): Location?

}