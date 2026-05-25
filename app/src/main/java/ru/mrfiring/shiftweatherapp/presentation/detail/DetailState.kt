package ru.mrfiring.shiftweatherapp.presentation.detail

import ru.mrfiring.shiftweatherapp.domain.models.WeatherContainer

sealed interface DetailState {
    data object Loading : DetailState
    data object Error : DetailState
    data class Content(val container: WeatherContainer) : DetailState
}