package ru.mrfiring.shiftweatherapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetFavoriteCitiesUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus

interface FavoriteComponent {

    val favorites: LiveData<List<City>>
    val status: LiveData<ApiStatus>

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

    private var _favorites = MutableLiveData<List<City>>(emptyList())
    override val favorites: LiveData<List<City>> = _favorites

    private val _status = MutableLiveData<ApiStatus>()
    override val status: LiveData<ApiStatus> = _status

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