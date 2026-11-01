package ru.mrfiring.shiftweatherapp.presentation.detail

import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.domain.models.WeatherContainer

sealed interface DetailState {
    data object Loading : DetailState
    data object Error : DetailState
    data object LocationPermissionMissing : DetailState
    data class Content(
        val container: WeatherContainer,
        val city: City?
    ) : DetailState
}
