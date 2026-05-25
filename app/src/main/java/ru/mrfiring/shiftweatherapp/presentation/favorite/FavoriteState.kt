package ru.mrfiring.shiftweatherapp.presentation.favorite

import ru.mrfiring.shiftweatherapp.domain.models.City

sealed interface FavoriteState {
    data object Loading : FavoriteState
    data object Error : FavoriteState
    data class Content(val favorites: List<City>) : FavoriteState
}
