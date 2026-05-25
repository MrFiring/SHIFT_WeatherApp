package ru.mrfiring.shiftweatherapp.presentation.favorite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetFavoriteCitiesUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.City

interface FavoriteComponent {

    val state: Value<FavoriteState>

    fun onRetry()
    fun onLongPress(city: City)
    fun onSelectCity(city: City)
}

class FavoriteComponentImpl(
    private val componentContext: ComponentContext,
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
    private val updateCityUseCase: UpdateCityUseCase,
    private val selectCityHandler: (City) -> Unit,
) : FavoriteComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableValue<FavoriteState>(FavoriteState.Loading)
    override val state: Value<FavoriteState> = _state

    init {
        loadFavorites()
    }

    override fun onRetry() {
        loadFavorites()
    }

    private fun loadFavorites() {
        scope.launch {
            try {
                _state.value = FavoriteState.Loading
                _state.value = FavoriteState.Content(getFavoriteCitiesUseCase())
            } catch (ex: Exception) {
                _state.value = FavoriteState.Error
            }
        }
    }

    override fun onLongPress(city: City) {
        scope.launch {
            city.favorite = !city.favorite
            updateCityUseCase(city = city)
            loadFavorites()
        }
    }

    override fun onSelectCity(city: City) {
        selectCityHandler(city)
    }
}