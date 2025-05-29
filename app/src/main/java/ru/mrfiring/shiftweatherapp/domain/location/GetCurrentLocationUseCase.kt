package ru.mrfiring.shiftweatherapp.domain.location

class GetCurrentLocationUseCase(private val repository: LocationRepository) :
    suspend () -> Location? by repository::getCurrent