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
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus

interface FavoriteComponent {

    val favorites: Value<List<City>>
    val status: Value<ApiStatus>

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

    private var _favorites = MutableValue<List<City>>(emptyList())
    override val favorites: Value<List<City>> = _favorites

    private val _status = MutableValue(ApiStatus.LOADING)
    override val status: Value<ApiStatus> = _status

    init {
        bindFavorites()
    }

    override fun onRetry() {
        bindFavorites()
    }

    private fun bindFavorites() {
        scope.launch {
            try {
                _status.value = ApiStatus.LOADING
                _favorites.value = getFavoriteCitiesUseCase()
                _status.value = ApiStatus.DONE
            } catch (ex: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onLongPress(city: City) {
        scope.launch {
            city.favorite = !city.favorite
            updateCityUseCase(city = city)
            bindFavorites()
        }
    }

    override fun onSelectCity(city: City) {
        selectCityHandler(city)
    }
}