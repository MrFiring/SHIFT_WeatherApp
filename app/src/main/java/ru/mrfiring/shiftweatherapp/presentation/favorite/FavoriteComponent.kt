package ru.mrfiring.shiftweatherapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetFavoriteCitiesLiveDataUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus

interface FavoriteComponent {

    val favorites: LiveData<List<DomainCity>>
    val status: LiveData<ApiStatus>

    fun onRetry()
    fun onLongPress(city: DomainCity)
    fun onSelectCity(city: DomainCity)
}

class FavoriteComponentImpl(
    private val componentContext: ComponentContext,
    private val getFavoriteCitiesLiveDataUseCase: GetFavoriteCitiesLiveDataUseCase,
    private val updateCityUseCase: UpdateCityUseCase,
    private val selectCityHandler: (DomainCity) -> Unit,
) : FavoriteComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var _favorites = MutableLiveData<List<DomainCity>>()
    override val favorites: LiveData<List<DomainCity>> = _favorites

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
                _favorites = getFavoriteCitiesLiveDataUseCase() as MutableLiveData<List<DomainCity>>
                _status.value = ApiStatus.DONE
            } catch (ex: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onLongPress(city: DomainCity) {
        scope.launch {
            city.favorite = !city.favorite
            updateCityUseCase(city = city)
        }
    }

    override fun onSelectCity(city: DomainCity) {
        selectCityHandler(city)
    }
}