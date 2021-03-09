package ru.mrfiring.shiftweatherapp.presentation.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetFavoriteCitiesLiveDataUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus

class FavoriteScreenViewModel(
    application: Application,
    private val getFavoriteCitiesLiveDataUseCase: GetFavoriteCitiesLiveDataUseCase,
    private val updateCityUseCase: UpdateCityUseCase
) : AndroidViewModel(application) {

    private var _favorites = MutableLiveData<List<DomainCity>>()
    val favorites: LiveData<List<DomainCity>>
        get() = _favorites

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        bindFavorites()
    }

    private fun bindFavorites() = viewModelScope.launch {
        try {
            _status.value = ApiStatus.LOADING
            _favorites = getFavoriteCitiesLiveDataUseCase() as MutableLiveData<List<DomainCity>>
            _status.value = ApiStatus.DONE
        } catch (ex: Exception) {
            Log.e("FavoriteScreenViewModel", ex.stackTrace.toString())
            _status.value = ApiStatus.ERROR
        }

    }

    fun onRetry() = bindFavorites()

    fun onLongPress(city: DomainCity) = viewModelScope.launch {
        city.favorite = !city.favorite
        updateCityUseCase(city = city)
    }

}